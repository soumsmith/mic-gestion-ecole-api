package com.vieecoles.steph.dto;
import java.io.InputStream;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MultipartBody {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("prefixUri")
    @PartType(MediaType.TEXT_PLAIN)
    public String prefixUri;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
}
