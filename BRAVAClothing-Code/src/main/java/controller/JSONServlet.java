package controller;

import jakarta.servlet.annotation.WebServlet;
import model.prodotto.Prodotto;
import model.prodotto.ProdottoDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/JSON")
public class JSONServlet extends HttpServlet {
    private static final long serialVersionUID= 1L;
    public JSONServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data=request.getParameter("data");
        ProdottoDAO proDAO=new ProdottoDAO();
        ArrayList<Prodotto> lista=proDAO.doRetrieveAll();
        ArrayList<Prodotto> result= new ArrayList<>();
        for(int i=0; i<lista.size(); i++){
            if (lista.get(i).getNome().toLowerCase().contains(data.toLowerCase()))
                result.add(lista.get(i));
        }

        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().append("[");
        String nome;
        long id;
        for (int i=0; i<result.size(); i++){
            nome = result.get(i).getNome();
            id = result.get(i).getIdProdotto();
            String idJSON = String.valueOf(id);
            response.getWriter().append("{\"nome\":\"").append(nome).append("\", ");
            response.getWriter().append("\"idProdotto\":\"").append(idJSON).append("\"");
            response.getWriter().append("}");
            if (i!=result.size()-1)
                response.getWriter().append(",");
        }
        response.getWriter().append("]");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
