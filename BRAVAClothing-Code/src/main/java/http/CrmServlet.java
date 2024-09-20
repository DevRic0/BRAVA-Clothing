package http;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import model.categoria.Categoria;
import model.categoria.CategoriaDAO;
import model.cliente.Cliente;
import model.cliente.ClienteDAO;
import model.magazzino.Magazzino;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "CrmServlet", value = "/crm/*")
@MultipartConfig
public class CrmServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        ProdottoDAO proDAO=new ProdottoDAO();
        ClienteDAO cliDAO=new ClienteDAO();
        CategoriaDAO catDAO=new CategoriaDAO();
        OrdineDAO ordDAO=new OrdineDAO();
        String address=getServletContext().getContextPath();

        String path=(request.getPathInfo() != null) ? request.getPathInfo(): "/";
        switch (path) {
            case "/addpro": // ADMIN AGGIUNGE UN PRODOTTO
                String nomePro=request.getParameter("nome");
                String prezzo=request.getParameter("prezzo");
                double price=Double.parseDouble(prezzo);
                String descB=request.getParameter("descBreve");
                String descD=request.getParameter("descDett");
                String inOff=request.getParameter("offerta");
                boolean off=false;
                if (inOff.equals("true"))
                    off=true;
                String quan=request.getParameter("quantita");
                String Mag=request.getParameter("idMag");
                String Cat=request.getParameter("idCat");
                String Pro=request.getParameter("idPro");
                int idPro=Integer.parseInt(Pro);
                int idCat=Integer.parseInt(Cat);
                int idMag=Integer.parseInt(Mag);
                int quantita=Integer.parseInt(quan);
                Categoria cat=new Categoria();
                cat.setIdCategoria(idCat);
                CategoriaDAO categoriaDAO = new CategoriaDAO();
                cat = categoriaDAO.doRetrieveById(idCat);
                Magazzino mag=new Magazzino();
                mag.setIdMagazzino(idMag);
                //Upload File
                String updatePath = "C:"+ File.separator+"ProgramData" +File.separator + "MySQL" + File.separator + "MySQL Server 8.0" + File.separator + "Uploads" + File.separator;
                //String updatePath=System.getenv("Catalina") + File.separator + "Uploads"+ File.separator;
                Part filePart=request.getPart("img");
                String fileName= Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                InputStream stream = filePart.getInputStream();
                String linkImg = updatePath + fileName;
                File file= new File(linkImg);
                try{
                    Files.copy(stream,file.toPath());
                } catch (FileAlreadyExistsException e){
                    /* do nothing */
                }
                Prodotto p= null;
                try {
                    p = new Prodotto(fileName,idPro,nomePro,price,off,descB,descD,quantita,mag,cat);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                proDAO.addProdottoImg(p,cat,mag);
                response.sendRedirect(address+"/crm/product");
                break;
            case "/addcust": // ADMIN AGGIUNGE UN PROFILO UTENTE
                String nomeCust = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                String user = request.getParameter("username");
                String mail = request.getParameter("email");
                String indirizzo = request.getParameter("indirizzo");
                String tel = request.getParameter("tel");
                String newPass = request.getParameter("password");
                ArrayList<Cliente> cli=cliDAO.doRetrieveAll();
                int creaId =cli.size()+1;
                String admin=request.getParameter("admin");
                Boolean b=false;
                if (admin.equals("true"))
                    b=true;
                Cliente nuovo=new Cliente(cognome,nomeCust,mail,user,newPass,indirizzo,tel,creaId,b);
                cliDAO.addCliente(nuovo);
                response.sendRedirect(address+"/crm/customer");
                break;
            case "/addcat":  //ADMIN AGGIUNGE UNA CATEGORIA
                String nomeCat=request.getParameter("nome");
                String desc=request.getParameter("descrizione");
                String genere = request.getParameter("genere");
                ArrayList<Categoria> listaCat=catDAO.doRetrieveAll();
                int idCate=listaCat.size()+1;
                Categoria x=new Categoria(nomeCat,desc,idCate, genere);
                catDAO.addCategoria(x);
                response.sendRedirect(address+"/crm/category");
                break;
            case "/updatepro":  // AGGIORNO UN PRODOTTO
                nomePro = request.getParameter("nome");
                prezzo= request.getParameter("prezzo");
                descB= request.getParameter("descB");
                descD= request.getParameter("descD");
                inOff= request.getParameter("offerta");
                b=false;
                if (inOff.equals("true"))
                    b=true;
                String idProdotto=request.getParameter("selezioneMod");
                String idCategoria=request.getParameter("idCat");
                String idMagazzino=request.getParameter("idMag");
                quan=request.getParameter("quantita");
                price=Double.parseDouble(prezzo);
                quantita=Integer.parseInt(quan);
                idPro=Integer.parseInt(idProdotto);
                idCat=Integer.parseInt(idCategoria);
                cat=new Categoria();
                cat.setIdCategoria(idCat);
                idMag=Integer.parseInt(idMagazzino);
                mag=new Magazzino();
                mag.setIdMagazzino(idMag);
                //Upload File
                //C:\ProgramData\MySQL\MySQL Server 8.0
                updatePath = "C:"+ File.separator+"ProgramData" +File.separator + "MySQL" + File.separator + "MySQL Server 8.0" + File.separator + "Uploads" + File.separator;
                //updatePath=System.getenv("Catalina") + File.separator + "Uploads"+ File.separator;
                filePart=request.getPart("img");
                fileName= Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                stream = filePart.getInputStream();
                linkImg = updatePath + fileName;
                file= new File(linkImg);
                try{
                    Files.copy(stream,file.toPath());
                } catch (FileAlreadyExistsException e) {
                    /* do nothing */
                }
                try {
                    Prodotto newProdotto = new Prodotto(fileName,idPro,nomePro,price,b,descB,descD,quantita,mag,cat);
                    proDAO.doChangesWithImg(newProdotto);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                response.sendRedirect(address+"/crm/product");
                break;
            case "/updateord": // AGGIORNA UN ORDINE
                String idOrdine=request.getParameter("ordSelezionato");
                int idOrd=Integer.parseInt(idOrdine);
                Ordine oldOrdine=ordDAO.doRetrieveById(idOrd);
                String iva=request.getParameter("iva");
                double ivaOrd=Double.parseDouble(iva);
                String data=request.getParameter("data");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                df.setLenient (false);
                Date dataOrdine;
                Ordine newOrdine= new Ordine();
                try {
                    dataOrdine = df.parse (data);
                    java.sql.Date sqlDate = new java.sql.Date(dataOrdine.getTime());
                    newOrdine=new Ordine(ivaOrd, sqlDate,idOrd,oldOrdine.getCliente());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ordDAO.doChanges(newOrdine);
                response.sendRedirect(address+"/crm/order");
                break;
            case "/updatecat":  // AGGIORNA UNA CATEGORIA
                idCategoria=request.getParameter("catSelezionata");
                idCat=Integer.parseInt(idCategoria);
                nomeCat=request.getParameter("nome");
                desc=request.getParameter("desc");
                genere=request.getParameter("genere");
                cat=new Categoria(nomeCat,desc,idCat, genere);
                catDAO.doChanges(cat);
                response.sendRedirect(address+"/crm/category");
                break;
            case "/updatecust":  //AGGIORNA IL SUO PROFILO
                nomeCust = request.getParameter("nome");
                cognome = request.getParameter("cognome");
                user = request.getParameter("username");
                mail = request.getParameter("email");
                indirizzo = request.getParameter("indirizzo");
                tel = request.getParameter("telefono");
                String id = request.getParameter("idAdmin");
                int idIntero = Integer.parseInt(id);
                newPass = request.getParameter("password");
                cliDAO = new ClienteDAO();
                Cliente oldProfilo= cliDAO.doRetrieveById(idIntero);
                Cliente newProfilo =new Cliente(cognome,nomeCust,mail,user,newPass,indirizzo,tel,idIntero,true);
                if (oldProfilo.getPassword().equals(newPass))
                    cliDAO.doChanges(newProfilo);
                else
                    cliDAO.doChangesWithPass(newProfilo);
                response.sendRedirect(address+"/crm/profile");
                break;
            case "/deletepro":  //CANCELLA UN PRODOTTO
                String idDelete=request.getParameter("selezioneDelete");
                int idDelPro=Integer.parseInt(idDelete);
                ProdottoDAO prodottoDAO=new ProdottoDAO();
                prodottoDAO.deleteById(idDelPro);
                response.sendRedirect(address+"/crm/product");
                break;
            case "/profile":
                cliDAO=new ClienteDAO();
                Cliente c=cliDAO.doRetrieveById((long) session.getAttribute("id"));
                session.setAttribute("profilo",c);
                request.getRequestDispatcher("/WEB-INF/views/crm/profile.jsp").forward(request, response);
                break;
            case "/customer":  //DASHBOARD CLIENTI
                cliDAO= new ClienteDAO();
                cli=cliDAO.doRetrieveAll();
                session.setAttribute("listaCli",cli);
                request.getRequestDispatcher("/WEB-INF/views/crm/customer.jsp").forward(request, response);
                break;
            case "/category": //DASHBOARD CATEGORIE
                catDAO=new CategoriaDAO();
                ArrayList<Categoria> cate=catDAO.doRetrieveAll();
                session.setAttribute("listaCat",cate);
                request.getRequestDispatcher("/WEB-INF/views/crm/categories.jsp").forward(request, response);
                break;
            case "/order":  //DASHBOARD ORDINI
                ArrayList<Ordine> ord=ordDAO.doRetrieveAll();
                session.setAttribute("listaOrd",ord);
                request.getRequestDispatcher("/WEB-INF/views/crm/orders.jsp").forward(request, response);
                break;
            case "/product":  // DASHBOARD PRODOTTI
                proDAO=new ProdottoDAO();
                ArrayList<Prodotto> pro=proDAO.doRetrieveAll();
                session.setAttribute("listaPro",pro);
                request.getRequestDispatcher("/WEB-INF/views/crm/products.jsp").forward(request, response);
                break;
            case "/logout":  //LOGOUT ADMIN
                session.invalidate();
                response.sendRedirect(request.getContextPath()+"/cliente/secret");
                break;
            case "/dashboard":  //DASHBOARD GENERALE ADMIN
                String email,pass;
                email=(String) session.getAttribute("email");
                pass=(String) session.getAttribute("pass");
                if (email==null && pass==null ) { //SE PRIMA VOLTA
                    email = request.getParameter("email");
                    pass = request.getParameter("password");
                    session.setAttribute("email", email);
                    session.setAttribute("pass", pass);
                }
                cliDAO=new ClienteDAO();
                c=cliDAO.doRetrieveClienteByEmailPassword(email,pass);
                if (c!=null && c.isAdmin()) { //SE ADMIN
                        session.setAttribute("id",c.getIdCliente());
                        ordDAO=new OrdineDAO();
                        ord=ordDAO.doRetrieveAll();
                        request.setAttribute("numeroOrdini",ord.size());
                        catDAO=new CategoriaDAO();
                        cate=catDAO.doRetrieveAll();
                        request.setAttribute("numeroCategorie",cate.size());
                        cli= cliDAO.doRetrieveAll();
                        request.setAttribute("numeroClienti",cli.size());
                        proDAO=new ProdottoDAO();
                        pro= proDAO.doRetrieveAll();
                        request.setAttribute("numeroProdotti",pro.size());
                        request.getRequestDispatcher("/WEB-INF/views/crm/dashboard.jsp").forward(request, response);
                }else{ // ALTRIMENTI NON SEI AUTORIZZATO
                    session.removeAttribute("email");
                    session.removeAttribute("pass");
                    session.removeAttribute("id");
                    session.setAttribute("failedAdmin",true);
                    response.sendRedirect(request.getContextPath() + "/cliente/secret");     //alert da aggiungere
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }


}
