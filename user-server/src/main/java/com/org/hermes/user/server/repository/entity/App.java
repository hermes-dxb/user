package com.org.hermes.user.server.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app")
public class App extends Audit{
    @Id
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_secret")
    private String appSecret;
    @Column(name = "app_name")
    private String appName;
    @Column(name = "auth_db")
    private String authSource;
    @Column(name = "auth_type")
    private String authType;
    @Column(name = "redirection_url")
    private String redirectionUrl;
    @Column(name = "description")
    private String description;
}
