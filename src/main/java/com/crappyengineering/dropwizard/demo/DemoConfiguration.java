package com.crappyengineering.dropwizard.demo;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class DemoConfiguration extends Configuration {
    // TODO: implement service configuration


    @NotNull
    @Valid
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();


    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory(){
        return dataSourceFactory;
    }
}
