package clickme;
//import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
//import com.sun.tools.classfile.ConstantPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

//import com.sun.tools.classfile.ConstantPool;

@Controller
public class HelloController {
    @Autowired
    UrlService urlService;

//    @GetMapping("/")
//    public String submit(Model model) {
//        model.addAttribute("name", "World");
//        return "hello";
//    }

    @GetMapping(value = {"/", "/home"})
    public String urlForm(Model model) {
//        System.out.println(" Inside get /");

        model.addAttribute("url", new Url());
        return "url";
    }

    @PostMapping(value = {"/url"})
    public String urlSubmit(@Valid @ModelAttribute Url url, BindingResult bindingResult) {
//        System.out.println(" Inside post /url"+url.getLong_url()+" "+url.getShort_url());


        if(bindingResult.hasErrors()){
            return "url";
        }
        boolean added = urlService.addUrl(url);

        if(!added) {
            bindingResult.rejectValue("short_url", "short_url.repeated", "This URL has already been used!");
        }
        if(bindingResult.hasErrors()){
            return "url";
        }

        return "result";
    }

    @GetMapping("/{url}")
    public String urlRedirect(@PathVariable("url") String short_url,
                              Model model) {

        String long_url = urlService.getLongUrl(short_url);
        if(long_url!=null)
            return "redirect:" + long_url;
        else
        {
            model.addAttribute("url", new Url());
            return "url";
        }
    }
}
