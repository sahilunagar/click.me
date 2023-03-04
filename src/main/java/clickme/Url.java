package clickme;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=1, message = "Required!")
    private String long_url;

    @Pattern(regexp = "^[a-zA-Z0-9\\.\\-]*", message="characters must be from a-z,A-Z,0-9")
    private String short_url;

    @Id
    @GenericGenerator(name="any", strategy = "increment")
    @GeneratedValue(generator = "any")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLong_url() {
        return long_url;
    }

    public void setLong_url(String long_url) {
        this.long_url = long_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

}
