package we.lala.winter.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lala_memo")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LalaMemo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "check")
    private boolean check;

    @Column(name = "created_dt")
    private Date createDt;

    @Column(name = "modified_dt")
    private Date modifiedDt;
}
