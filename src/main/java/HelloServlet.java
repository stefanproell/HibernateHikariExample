/**
 * Created by stefan on 13.06.16.
 */


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Random;


public class HelloServlet extends HttpServlet {
    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();
        addRandomNumber(req);
        out.println("There are " + countNumbers(req) + " random numbers");


        List<RandomNumberPOJO> numbers = getAllRandomNumbers(req,res);

        out.println("Random Numbers:");
        out.println("----------");

        for(RandomNumberPOJO record:numbers){
            out.println("ID: " + record.getId() + "\t :\t" + record.getRandomNumber());
        }

        out.close();

    }

    /**
     * Create a new random number and store it the database
     * @param request
     */
    private void addRandomNumber(HttpServletRequest request){
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

    /**
     * Get a list of all RandomNumberPOJO objects
     * @param request
     * @param response
     * @return
     */
    private List<RandomNumberPOJO> getAllRandomNumbers(HttpServletRequest request, HttpServletResponse response){
        SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        TypedQuery<RandomNumberPOJO> query = session.createQuery(
                "from RandomNumberPOJO", RandomNumberPOJO.class);

        List<RandomNumberPOJO> numbers =query.getResultList();



        tx.commit();
        session.close();

        return numbers;


    }

    /**
     * Count records
     * @param request
     * @return
     */
    private int countNumbers(HttpServletRequest request){
        SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        String count = session.createQuery("SELECT COUNT(id) FROM RandomNumberPOJO").uniqueResult().toString();

        int rowCount = Integer.parseInt(count);

        tx.commit();
        session.close();
        return rowCount;
    }
}