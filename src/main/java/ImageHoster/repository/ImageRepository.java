package ImageHoster.repository;

import ImageHoster.model.Image;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class ImageRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //method to return all immages
    public List<Image> getAllImages() {
        EntityManager em=emf.createEntityManager();
        TypedQuery<Image> query = em.createQuery("SELECT p from Image p", Image.class);
        List<Image> resultList = query.getResultList();
        return resultList;
    }
    //The method receives the Image object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Image uploadImage(Image newImage) {
        //Complete the method
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            //persist() method changes the state of the model object from transient state to persistence state
            em.persist(newImage);
            transaction.commit();
            System.out.println("sucess");
        } catch (Exception e) {
            System.out.println("Failed");
            transaction.rollback();
        }
        return newImage;
    }
    public Image getImageByTitle(String title) {
        EntityManager em = emf.createEntityManager();
//        System.out.println(title);
        try {
            TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i where i.title =:title", Image.class).setParameter("title", title);
            return typedQuery.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
