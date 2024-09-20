package controller;

import jakarta.servlet.annotation.WebServlet;
import model.cliente.Cliente;
import model.cliente.ClienteDAO;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import model.prodottoordine.ProdottoOrdine;
import model.prodottoordine.ProdottoOrdineDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "ClienteServlet", value = "/cliente/*")
public class ClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        String address=getServletContext().getContextPath();
        ClienteDAO cliDAO=new ClienteDAO();
        OrdineDAO ordDAO=new OrdineDAO();
        ProdottoDAO proDAO=new ProdottoDAO();
        ProdottoOrdineDAO poDAO=new ProdottoOrdineDAO();
        String path=(request.getPathInfo() != null) ? request.getPathInfo(): "/";
        switch (path){
            case "/deletepro": //CANCELLAZIONE PRODOTTO DA CARRELLO
                int idDel=Integer.parseInt(request.getParameter("delete"));
                ArrayList<ProdottoOrdine> elenco=((ArrayList<ProdottoOrdine>) session.getAttribute("elencocart"));
                ArrayList<Prodotto> lista=((ArrayList<Prodotto>) session.getAttribute("cart"));
                for(int i=0; i<elenco.size(); i++){
                    if (elenco.get(i).getProdotto().getIdProdotto()==idDel) {
                        Prodotto x=lista.get(i);
                        int temp=x.getQuantita();
                        x.setQuantita(temp+elenco.get(i).getQuantita());
                        proDAO.doChanges(x);
                        elenco.remove(i);
                        lista.remove(i);
                    }
                }
                double totale=0;
                for(int i=0; i<lista.size(); i++){
                    double temp=lista.get(i).getPrezzo();
                    long idPro=lista.get(i).getIdProdotto();
                    if (elenco.get(i).getProdotto().getIdProdotto()==idPro){
                        int quantita=elenco.get(i).getQuantita();
                        totale+=(quantita*temp);
                    }
                }  // calcolo totale carrello mando in sessione
                session.setAttribute("totale",totale);

                session.setAttribute("elencocart",elenco);
                session.setAttribute("cart",lista);
                response.sendRedirect(address+"/cliente/carrello");
                break;
            case "/acquistacarrello": //ACQUISTO DA CARRELLO
                Cliente c=(Cliente) session.getAttribute("profilo");
                java.util.Date dataOrdine=new Date();
                java.sql.Date sqlDate = new java.sql.Date(dataOrdine.getTime());
                ArrayList<Ordine> listaOrd=ordDAO.doRetrieveAll();
                long newId=listaOrd.get(listaOrd.size()-1).getIdOrdine()+1;
                Ordine ord=new Ordine(21,sqlDate,newId,c);
                ordDAO.addOrdine(ord,c);

                elenco=(ArrayList<ProdottoOrdine>) session.getAttribute("elencocart");
                lista=(ArrayList<Prodotto>) session.getAttribute("cart");
                for(int i=0; i<elenco.size(); i++){
                    poDAO.addProdottoOrdine(ord,lista.get(i),elenco.get(i).getQuantita());
                }
                totale=0;
                session.setAttribute("totale",totale);
                elenco=new ArrayList<>();
                lista=new ArrayList<>();
                session.setAttribute("elencocart",elenco);
                session.setAttribute("cart",lista);
                request.getRequestDispatcher("/WEB-INF/views/site/ordineeffettuato.jsp").forward(request, response);
                break;
            case "/inputcarrello": //INSERIMENTO IN CARRELLO
                Boolean b=(Boolean)session.getAttribute("log"); // DA SESSIONE PRENDO LOGGATO
                ArrayList<Prodotto> cart;
                if (b==null){ //CONTROLLO SE PRIMA VOLTA
                    b=false;
                    session.setAttribute("log",b);
                }
                if (!b) { //CONTROLLO SE NON LOGGATO
                    request.getRequestDispatcher("/WEB-INF/views/site/signin.jsp").forward(request, response);
                }else { //SE LOGGATO PRENDO tutto
                    elenco=(ArrayList<ProdottoOrdine>) session.getAttribute("elencocart");
                    ProdottoOrdine po=new ProdottoOrdine();
                    if (elenco==null)
                        elenco=new ArrayList<>();

                    int id=Integer.parseInt(request.getParameter("id"));
                    int selezionati=Integer.parseInt(request.getParameter("selected"));
                    session.setAttribute("selezionati",selezionati);

                    Prodotto pro = proDAO.doRetrieveById(id);//RECUPERO PRODOTTO
                    pro.setQuantita(pro.getQuantita()-selezionati);//DIMINUISCO I SELEZIONATI
                    proDAO.doChanges(pro);

                    boolean add=false;
                    for(int i=0; i<elenco.size(); i++){
                        if (elenco.get(i).getProdotto().getIdProdotto()==id){
                            int temp=elenco.get(i).getQuantita();
                            int somma=temp+selezionati;
                            elenco.get(i).setQuantita(somma);
                            add=true;
                        }
                    }
                    if (!add) {
                        po.setProdotto(pro); //setto ProdottoOrdine
                        po.setQuantita(selezionati);
                        elenco.add(po); // aggiungo ad Elenco
                    }
                    session.setAttribute("elencocart",elenco);

                    cart = (ArrayList<Prodotto>) session.getAttribute("cart");
                    if (cart == null)  //SE PRIMA VOLTA DICHIARO
                        cart = new ArrayList<>();

                    if(!add) {
                        cart.add(pro); //aggiungo a cart il prodotto se non c'è gia
                        session.setAttribute("cart", cart);
                    }
                    totale=0;
                    for(int i=0; i<cart.size(); i++){
                        double temp=cart.get(i).getPrezzo();
                        long idPro=cart.get(i).getIdProdotto();
                        if (elenco.get(i).getProdotto().getIdProdotto()==idPro){
                                int quantita=elenco.get(i).getQuantita();
                                totale+=(quantita*temp);
                        }
                    }  // calcolo totale carrello mando in sessione
                    session.setAttribute("totale",totale);

                    response.sendRedirect(address+"/cliente/carrello");
                }
                break;
            case "/carrello": //VIEW CARRELLO
                b=(Boolean) session.getAttribute("log"); // DA SESSIONE PRENDO LOGGATO
                if (b==null){ //CONTROLLO SE PRIMA VOLTA
                    b=false;
                    session.setAttribute("log",b);
                }
                if (b) { //CONTROLLO SE LOGGATO E SE IL CART ESISTE SENO ISTANZIO
                    cart = (ArrayList<Prodotto>) session.getAttribute("cart");
                    if (cart != null)
                        request.getRequestDispatcher("/WEB-INF/views/site/cart.jsp").forward(request, response);
                    else {
                        cart=new ArrayList<>();
                        session.setAttribute("cart",cart);
                        elenco=(ArrayList<ProdottoOrdine>) session.getAttribute("elencocart");
                        if (elenco==null)
                            elenco=new ArrayList<>();

                        session.setAttribute("elencocart",elenco);
                        request.getRequestDispatcher("/WEB-INF/views/site/cart.jsp").forward(request, response);
                    }
                }else // ALTRIMENTI DEVE LOGGARE
                    request.getRequestDispatcher("/WEB-INF/views/site/signin.jsp").forward(request,response);
                break;
            case "/product": //VIEW PRODOTTO SINGOLO
                int id=Integer.parseInt(request.getParameter("id"));
                Prodotto pro=proDAO.doRetrieveById(id);
                session.setAttribute("prodotto",pro);
                request.getRequestDispatcher("/WEB-INF/views/site/singleproduct.jsp").forward(request, response);
                break;
            case "/signup": //PAGINA REGISTRAZIONE CLIENTE
                request.getRequestDispatcher("/WEB-INF/views/site/signup.jsp").forward(request,response);
                break;
            case "/showord": //MOSTRA ORDINE UTENTE
                int idOrdine=Integer.parseInt(request.getParameter("id"));
                ArrayList<ProdottoOrdine> result=poDAO.doRetrieveProdottiWithIdOrdine(idOrdine);
                ArrayList<Prodotto> showOrdine=new ArrayList<>();

                double totOrdine=0;
                for (int i=0; i<result.size(); i++){
                    Prodotto p=proDAO.doRetrieveById(result.get(i).getProdotto().getIdProdotto());
                    totOrdine+=(p.getPrezzo()*result.get(i).getQuantita());
                    showOrdine.add(p);
                }
                session.setAttribute("idOrdine",idOrdine);
                session.setAttribute("showOrdine",showOrdine);
                session.setAttribute("result",result);
                session.setAttribute("totOrdine",totOrdine);
                request.getRequestDispatcher("/WEB-INF/views/site/showordine.jsp").forward(request,response);
                break;
            case "/show": // SHOW PROFILO CLIENTE
                b=(Boolean) session.getAttribute("log");
                if (b) {
                    c=(Cliente) session.getAttribute("profilo");
                    listaOrd=ordDAO.doRetrieveByIdCliente(c.getIdCliente());
                    session.setAttribute("listaOrd",listaOrd);
                    request.getRequestDispatcher("/WEB-INF/views/site/account.jsp").forward(request, response);
                }else
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita" );
                break;
            case "/create": // CREAZIONE PROFILO CLIENTE
                String nomeCust = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                String user = request.getParameter("username");
                String mail = request.getParameter("email");
                String indirizzo = request.getParameter("indirizzo");
                String tel = request.getParameter("telefono");
                String newPass = request.getParameter("password");
                id=cliDAO.doRetrieveAll().size()+1;
                Cliente newProfilo=new Cliente(cognome,nomeCust,mail,user,newPass,indirizzo,tel,id,false);
                cliDAO.addCliente(newProfilo);
                request.getRequestDispatcher("/WEB-INF/views/site/registered.jsp").forward(request, response);
                break;
            case "/update": // AGGIORNAMENTO PROFILO CLIENTE
                nomeCust = request.getParameter("nome");
                cognome = request.getParameter("cognome");
                user = request.getParameter("username");
                mail = request.getParameter("email");
                indirizzo = request.getParameter("indirizzo");
                tel = request.getParameter("telefono");
                String idCliente = request.getParameter("id");
                int idIntero = Integer.parseInt(idCliente);
                newPass = request.getParameter("password");
                cliDAO = new ClienteDAO();
                Cliente oldProfilo= cliDAO.doRetrieveById(idIntero);
                newProfilo =new Cliente(cognome,nomeCust,mail,user,newPass,indirizzo,tel,idIntero,true);
                if (oldProfilo.getPassword().equals(newPass)) {
                    cliDAO.doChanges(newProfilo);
                    session.setAttribute("profilo",newProfilo);
                }else {
                    cliDAO.doChangesWithPass(newProfilo);
                    session.setAttribute("profilo",newProfilo);
                }
                response.sendRedirect(address+"/cliente/show");
                break;
            case "/logout": //LOGOUT CLIENTE
                session.setAttribute("log",false);
                session.invalidate();
                response.sendRedirect(address+"/cliente/sign");
                break;
            case "/secret": // LOGIN ADMIN
                request.getRequestDispatcher("/WEB-INF/views/crm/secret.jsp").forward(request,response);
                break;
            case "/sign":
                request.getRequestDispatcher("/WEB-INF/views/site/signin.jsp").forward(request,response);
                break;
            case "/signin": // LOGIN CLIENTE
                mail = request.getParameter("email");
                newPass = request.getParameter("password");
                c=cliDAO.doRetrieveClienteByEmailPassword(mail,newPass);
                if (c!=null){
                    listaOrd=ordDAO.doRetrieveByIdCliente(c.getIdCliente());
                    session.setAttribute("listaOrd",listaOrd);
                    session.setAttribute("profilo", c);
                    session.setAttribute("log",true);
                    request.getRequestDispatcher("/WEB-INF/views/site/account.jsp").forward(request,response);
                }else{
                    session.setAttribute("failedUtente", true);
                    request.getRequestDispatcher("/WEB-INF/views/site/signin.jsp").forward(request,response);
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Operazione non consentita" );

        }
    }
}
