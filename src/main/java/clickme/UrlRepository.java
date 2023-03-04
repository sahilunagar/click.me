package clickme;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Service
@Repository
@Transactional
public class UrlRepository {

    @Autowired
    GetConvertedUrl getConvertedUrl;

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "PU" );
    EntityManager em = emfactory.createEntityManager( );

    public boolean saveUrl(Url url){
        if(url.getShort_url().length() != 0){
            if(getUrlByShort_url(url.getShort_url()) != null)
                return false;
        }

        em.getTransaction().begin();
        Url updated_url = em.merge(url);
        url.setShort_url(getConvertedUrl.getShortUrl(updated_url));


        while(getUrlByShort_url(url.getShort_url())!=null) {
            try{
                em.remove(url);
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }

            url.setShort_url("");
            url.setId(updated_url.getId()+1);
            updated_url = em.merge(url);
            url.setShort_url(getConvertedUrl.getShortUrl(updated_url));
        }

        url.setShort_url(getConvertedUrl.getShortUrl(updated_url));

        try{
            em.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }

    public Url getUrlById(int id){
        Url url;
        try {
            url = em.find(Url.class, id);
            return url;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Url getUrlByShort_url(String short_url)
    {
        Url url = null;
        try{
            url = (Url) em.createQuery("SELECT u FROM Url u WHERE u.short_url = :short_url")
                    .setParameter("short_url", short_url)
                    .getSingleResult();
        }catch (NoResultException ex){
            System.out.println(ex.getMessage());
//            System.out.println(short_url);

        }
        return url;
    }
}
