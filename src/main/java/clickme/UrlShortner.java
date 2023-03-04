package clickme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UrlShortner{
    private ArrayList<Character> code;
    @Value("${padding_value}")
    private String padding_value;
    @Value("${prefix}")
    private String prefix;


    UrlShortner()
    {
//        prefix = "www.clickme.com/";

//        prefix = "localhost:8080/";

        code = new ArrayList<Character>();
        int i=0;
        for(int c=0; c<26; c++)
            code.add((char)('a'+c));
        for(int c=0; c<26; c++)
            code.add((char)('A'+c));
        for(int c=0; c<10; c++)
            code.add((char)('0'+c));
        code.add('-');
        code.add('.');
    }


    private String createShortUrl(long num){
        num += Long.parseLong(padding_value);
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int rem;
        if(num == 0) return "a";
        while(num > 0){
            rem = (int) (num%64);
            digits.add(rem);
            num /= 64;
        }
        String ans = "";
        for(int i=digits.size()-1; i>=0; i--)
            ans += code.get(digits.get(i));
        return ans;
    }

    public String convertToShortUrl(int id) {
        return createShortUrl(id);
    }

    public  int redirectTo(String short_url){
        String codeOfUrl = short_url.substring(prefix.length());
        ArrayList<Integer> digits = new ArrayList<Integer>();
        for(int i=0; i<codeOfUrl.length(); i++){
            digits.add(code.indexOf(codeOfUrl.charAt(i)));
        }
        int p = 0;
        int ans_id = 0;
        for(int i=digits.size()-1; i>=0; i--){
            ans_id += digits.get(i) * (int)(Math.pow(62,p));
            p++;
        }
        return ans_id;
    }
}
