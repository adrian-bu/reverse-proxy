package de.adrianbuch.reverse.proxy.domain.decoy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "DECOY_POST_PARAMETER")
public class DecoyPostParameter {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "DECOY_POST_PARAMETER_ID", length = 36)
    @JsonProperty("id")
    private String id;

    @JsonProperty("post-parameter-key")
    @Column(name = "POST_PARAMETER_KEY")
    private String key;

    @JsonProperty("post-parameter-value")
    @Column(name = "POST_PARAMETER_VALUE")
    private String value;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Decoy decoy;
}
