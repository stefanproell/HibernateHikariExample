/**
 * Created by stefan on 13.06.16.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class HelloServlet extends HttpServlet {
    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        InitialContext cxt = null;
        int count=-1;
        try {

            addUser(req);

            cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/TestDB" );
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(id) FROM CitationDB.RandomNumber";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                count = resultSet.getInt(1);
            }

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        out.println("Hello, world Stefan!");
        out.println("There are " + count + " random numbers");
        out.close();
    }

    private void addUser(HttpServletRequest request){
        SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        RandomNumberPOJO randomNumber = new RandomNumberPOJO();
        Random rand = new Random();
        int randomInteger = 1 + rand.nextInt((999) + 1);


        randomNumber.setRandomNumber(randomInteger);
        session.save(randomNumber);
        tx.commit();
        session.close();


    }
}