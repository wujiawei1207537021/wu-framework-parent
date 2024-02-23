package com.wu.freamwork.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 基础河流表
 *
 * @author ss
 * @since 2020-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)


public class OmTpsmPubOthInfrBasRiver implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;


    private String riverCode;


    private String riverName;


    private String administrativeRegionCode;


    private String parentNode;


    private String isParentNode;


    private String riverGrade;


    private String riverAdministrator;


    private String riverStartPoint;


    private String startLongitude;


    private String startLatitude;


    private String riverEndPoint;


    private String lng;


    private String lat;


    private String riverLength;


    private String riverId;


    private String basinId;


    private String mainStream;


    private String stream;


    private String important;


    private String tableId;


    private String vectorData;


    private String protectionSign;


    private String area;


    private String comment;


    private String deletionSign;


}
