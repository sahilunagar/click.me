package clickme;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GetConvertedUrl {

//    @Autowired
//    UrlRepository urlRepository;
    @Autowired
    UrlShortner urlShortner;

    public String getShortUrl(Url url) {
        // If custom url is not given then create one using id
        if(url.getShort_url().length() == 0)
            url.setShort_url(urlShortner.convertToShortUrl(url.getId()));

        return url.getShort_url();
    }

//    public String getLongUrl(String short_url){
//        return urlRepository.getUrlByShort_url(short_url).getLong_url();
//    }
}
