package clickme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    UrlRepository urlRepository;
//    @Autowired
//    GetConvertedUrl getConvertedUrl;

    @Value("${prefix}")
    private String prefix;

    public boolean addUrl(Url url) {
        boolean saved = urlRepository.saveUrl(url);
        if (saved)
            url.setShort_url(prefix + url.getShort_url());
        return saved;
    }

    public String getLongUrl(String short_url_code) {
        //if whole short url is given, then short_url = short_url_code
//        String short_url = "www.clickme.com/" + short_url_code;
        Url url =  urlRepository.getUrlByShort_url(short_url_code);
//        System.out.println(Long_url);
        if(url==null)
            return null;
        return url.getLong_url();
    }
}