package we.lala.winter.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "clipping")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Clipping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text_message")
    private String textMessage;

    @Column(name = "clipped_url")
    private String clippedUrl;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "numbering")
    private Integer numbering;

    @Column(name = "created_dt")
    private Date createDt;

    @Column(name = "modified_dt")
    private Date modifiedDt;
}
