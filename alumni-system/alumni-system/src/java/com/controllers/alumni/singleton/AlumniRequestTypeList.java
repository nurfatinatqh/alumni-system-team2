/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers.alumni.singleton;

/**
 *
 * @author ainal farhan
 */
public interface AlumniRequestTypeList {
    String REQUEST_TYPE_VIEW_ALUMNI_LIST = "viewAlumniList";
    String REQUEST_TYPE_UPDATE_ALUMN_PROFILE_PICTURE = "updateAlumniProfilePicture";
    String REQUEST_TYPE_MANAGE_ALUMNUS_ALUMNA_INFO = "manageAlumnusAlumnaInfo";
    String REQUEST_TYPE_MANAGE_SELECTED_ALUMNI_INFO = "manageSelectedAlumniInfo";
    String REQUEST_TYPE_UPDATE_ALUMNI_INFO = "updateAlumniInfo";
    String REQUEST_TYPE_DELETE_UPDATE_ALUMNI_INFO = "deleteOrUpdateAlumniInfo";
    String REQUEST_TYPE_CONFIRMATION_TO_DELETE_ALUMNI_INFO = "confirmDeleteAlumniInfo";
    String REQUEST_TYPE_VIEW_ALUMNI_LIST_AT_SELECTED_PAGE = "goToSelectedPage";
    String REQUEST_TYPE_FILTER_ALUMNI_INFO = "filterAlumniInfo";
    String REQUEST_TYPE_SEARCH_ALUMNI = "searchAlumni";
}
