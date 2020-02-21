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
public class Clipping {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text_message")
    private String textMessage;


    @Column(name = "clipped_url")
    private String clippedUrl;

    @Column(name = "check")
    private boolean check;

    @Column(name = "order")
    private int order;

    @Column(name = "created_dt")
    private Date createDt;

    @Column(name = "modified_dt")
    private Date modifiedDt;
}
