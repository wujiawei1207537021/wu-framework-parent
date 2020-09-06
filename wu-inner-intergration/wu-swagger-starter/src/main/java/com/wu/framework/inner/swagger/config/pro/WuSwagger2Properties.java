package com.wu.framework.inner.swagger.config.pro;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "wu.swagger")
public class WuSwagger2Properties extends ApiInfo {

    public static final Contact DEFAULT_CONTACT = new Contact("", "", "");
    public static final WuSwagger2Properties DEFAULT ;
    private  String version;
    private  String title;
    private  String description;
    private  String termsOfServiceUrl;
    private  String license;
    private  String licenseUrl;
    private  Contact contact;
    private  List<VendorExtension> vendorExtensions;




    public WuSwagger2Properties() {
        super("Api Documentation", "Api Documentation", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }

    static {
        DEFAULT = new WuSwagger2Properties("Api Documentation", "Api Documentation", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }

    public WuSwagger2Properties(String title, String description, String version, String termsOfServiceUrl, Contact contact, String license, String licenseUrl, Collection<VendorExtension> vendorExtensions) {
        super(title, description, version, termsOfServiceUrl, contact, license, licenseUrl, vendorExtensions);
        this.version = version;
        this.title = title;
        this.description = description;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.license = license;
        this.licenseUrl = licenseUrl;
        this.contact = contact;
        this.vendorExtensions = Lists.newArrayList(vendorExtensions);
    }
}
