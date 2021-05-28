package de.adrianbuch.reverse.proxy.domain.request;

import java.security.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "HTTP_REQUEST")
public class HTTPRequest {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @JsonProperty("id")
    @Column(name = "HTTP_REQUEST_ID", length = 36)
    protected String id;

    @Nullable
    @JsonProperty("username")
    @Column(name = "USERNAME")
    protected String username;

    @Nullable
    @JsonProperty("authentication-token")
    @Column(name = "AUTHENTICATION_TOKEN")
    protected String authenticationToken;

    @Nullable
    @JsonProperty("remote-source-ip-address")
    @Column(name = "REMOTE_SOURCE_IP_ADDRESS")
    protected String remoteSourceIPAddress;

    @Basic
    @JsonProperty("request-received")
    @Column(name = "REQUEST_RECEIVED")
    protected Timestamp requestReceived;

    @Nullable
    @JsonProperty("http-request-is-malicious")
    @Column(name = "HTTP_REQUEST_IS_MALICIOUS")
    protected Boolean isMalicious;
}
