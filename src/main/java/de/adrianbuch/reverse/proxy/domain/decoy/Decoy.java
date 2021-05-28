package de.adrianbuch.reverse.proxy.domain.decoy;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

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
@Table(name = "DECOY")
public class Decoy {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JsonProperty("decoy-id")
    @Column(name = "DECOY_ID", length = 36)
    protected String id;

    @Enumerated(EnumType.STRING)
    @JsonProperty("decoy-type")
    @Column(name = "DECOY_TYPE", columnDefinition = "enum('FORM_PARAMETER','HTTP_COMMENT', 'UNUSED_JAVASCRIPT_METHOD')")
    protected DecoyType decoyType;

    @Nullable
    @JsonProperty("decoy-description")
    @Column(name = "DECOY_DESCRIPTION")
    protected String decoyDescription;

    @Nullable
    @JsonProperty("decoy-name")
    @Column(name = "DECOY_NAME")
    protected String decoyName;

    @Basic
    @JsonProperty("decoy-last-modified")
    @Column(name = "DECOY_LAST_MODIFIED")
    protected Timestamp decoyLastModified;

    @Basic
    @JsonProperty("decoy-created")
    @Column(name = "DECOY_CREATED")
    protected Timestamp decoyCreated;

    @Enumerated(EnumType.STRING)
    @JsonProperty("decoy-trigger")
    @Column(name = "DECOY_TRIGGER", columnDefinition = "enum('URL_PATH','ALWAYS')")
    protected DecoyTrigger decoyTrigger;

    @JsonManagedReference
    @JsonProperty("decoy-parameters")
    @OneToMany(mappedBy = "decoy", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER, orphanRemoval = true)
    private List<DecoyPostParameter> parameters = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "DECOY_HTML_COMMENT", joinColumns = @JoinColumn(name = "DECOY_ID"))
    @JsonProperty("decoy-html-comments")
    @Column(name = "DECOY_HTML_COMMENTS")
    protected List<String> comments = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "DECOY_UNUSED_JAVASCRIPT_METHOD", joinColumns = @JoinColumn(name = "DECOY_ID"))
    @JsonProperty("decoy-unused-javascript-methods")
    @Column(name = "DECOY_UNUSED_JAVASCRIPT_METHODS", columnDefinition = "text", length = 500000)
    protected List<String> unusedJavascriptMethods = new ArrayList<>();
}
