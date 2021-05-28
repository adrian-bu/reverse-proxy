package de.adrianbuch.reverse.proxy.domain.detection;

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
@Table(name = "DETECTION_POINT")
public class DetectionPoint {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JsonProperty("detection-point-id")
    @Column(name = "DETECTION_POINT_ID", length = 36)
    protected String detectionPointId;

    @Enumerated(EnumType.STRING)
    @JsonProperty("detection-point-type")
    @Column(name = "DETECTION_POINT_TYPE", columnDefinition = "enum('URL_PATH','POST_PARAMETER', 'APPLICATION_HONEYTOKEN')")
    protected DetectionPointType detectionPointType;

    @Nullable
    @JsonProperty("detection-point-description")
    @Column(name = "DETECTION_POINT_DESCRIPTION")
    protected String detectionPointDescription;

    @Nullable
    @JsonProperty("detection-point-name")
    @Column(name = "DETECTION_POINT_NAME")
    protected String detectionPointName;

    @Basic
    @JsonProperty("detection-point-last-modified")
    @Column(name = "DETECTION_POINT_LAST_MODIFIED")
    protected Timestamp detectionPointLastModified;

    @Basic
    @JsonProperty("detection-point-created")
    @Column(name = "DETECTION_POINT_CREATED")
    protected Timestamp detectionPointCreated;

    @JsonManagedReference
    @JsonProperty("detection-point-parameters")
    @OneToMany(mappedBy = "detectionPoint", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER, orphanRemoval = true)
    protected List<DetectionPointPostParameter> parameters = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "DETECTION_POINT_URL", joinColumns = @JoinColumn(name = "DETECTION_POINT_ID"))
    @JsonProperty("detection-point-urls")
    @Column(name = "DETECTION_POINT_URLS")
    protected List<String> urls;

    @ElementCollection
    @CollectionTable(name = "DETECTION_POINT_APPLICATION_HONEYTOKEN", joinColumns = @JoinColumn(name = "DETECTION_POINT_ID"))
    @JsonProperty("detection-point-application-honeytokens")
    @Column(name = "DETECTION_POINT_APPLICATION_HONEYTOKENS")
    protected List<String> applicationHoneytokens;
}
