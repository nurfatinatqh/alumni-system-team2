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
public interface AlumniSQLStatementList {
    // RETRIEVE SQL STATEMENT
    
    String SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_ALUMNI = "SELECT * FROM alumni;";
    String SQL_STATEMENT_RETRIEVE_ALL_FROM_TABLE_USER_WHERE_ROLE_IS_ALUMNI = "SELECT * FROM users WHERE role = 'alumni';";
    
    // UPDATE SQL STATEMENT
    String SQL_STATEMENT_UPDATE_SELECTED_ALUMNI_INFO = "UPDATE `alumni` "
            + "SET `alumniAddress1` = ?, `alumniAddress2` = ?, `alumniAddressCity` = ?, `alumniAddressCountry` = ?, "
                + "`alumniAddressPostCode` = ?, `alumniAddressState` = ?, `alumniBatchDiploma` = ?, `alumniBatchBachelor` = ?, "
                + "`alumniBatchMaster` = ?, `alumniCurEmployer` = ?, `alumniCurJob` = ?, `alumniCurSalary` = ?, "
                + "`alumniStartStudyYearDiploma` = ?, `alumniStartStudyYearBachelor` = ?, `alumniStartStudyYearMaster` = ?, "
                + "`alumniFieldOfSpecializationDiploma` = ?, `alumniFieldOfSpecializationBachelor` = ?, `alumniFieldOfSpecializationMaster` = ?, "
                + "`alumniGraduateYearDiploma` = ?, `alumniGraduateYearBachelor` = ?, `alumniGraduateYearMaster` = ?, `alumniPrevEmployer` = ?, "
                + "`alumniPrevJob` = ?, `alumniPrevSalary` = ?, "
                + "`alumniProfStatus` = ?, `alumniProfStatusYearGained` = ?, `employerAddress1` = ?, `employerAddress2` = ?, "
                + "`employerAddressCity` = ?, `employerAddressCountry` = ?, `employerAddressPostCode` = ?, `employerAddressState` = ? "
            + "WHERE `alumni`.`alumniID` = ?;";
    String SQL_STATEMENT_UPDATE_SELECTED_ALUMNI_PROFILE_PICTURE = "UPDATE `alumni` SET `alumniProfilePicture` = ? WHERE `alumni`.`alumniID` = ?;";
    
    // DELETE SQL STATEMENT
    String SQL_STATEMENT_DELETE_SELECTED_ALUMNI = "DELETE FROM `alumni` WHERE `alumni`.`alumniID` = ?";
    String SQL_STATEMENT_DELETE_SELECTED_USER = "DELETE FROM `users` WHERE `users`.`userID` = ?";
}
