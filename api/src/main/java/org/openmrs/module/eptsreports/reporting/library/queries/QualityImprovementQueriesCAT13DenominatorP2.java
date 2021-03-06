package org.openmrs.module.eptsreports.reporting.library.queries;

public interface QualityImprovementQueriesCAT13DenominatorP2 {

  class QUERY {

    public static final String findPatientsWhoArePregnantInclusionPeriodByB1 =
        " SELECT tx_new.patient_id FROM ( "
            + " SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
            + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
            + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
            + " GROUP BY p.patient_id "
            + " ) art_start "
            + " GROUP BY patient_id  "
            + " ) tx_new "
            + " INNER JOIN "
            + " ( "
            + " SELECT p.patient_id, cc.encounter_datetime as encounter_datetime "
            + " FROM patient p "
            + " INNER JOIN encounter cc ON p.patient_id = cc.patient_id "
            + " INNER JOIN obs o ON cc.encounter_id = o.encounter_id "
            + " WHERE cc.voided = 0 AND cc.encounter_type = 6  AND cc.location_id = :location "
            + " AND cc.encounter_datetime BETWEEN :startInclusionDate AND :endInclusionDate "
            + " AND o.concept_id = 1982 AND o.value_coded = 1065 AND o.voided = 0 "
            + " ) consulta ON consulta.patient_id = tx_new.patient_id "
            + " INNER JOIN person pe ON tx_new.patient_id = pe.person_id "
            + " WHERE tx_new.art_start_date = consulta.encounter_datetime "
            + " AND (TIMESTAMPDIFF(year, birthdate, :endInclusionDate)) >= 15 AND birthdate IS NOT NULL and pe.voided = 0 and pe.gender = 'F' ";

    public static final String findPatientsWhoArePregnantInFirstConsultationInclusionPeriodByB2 =
        " SELECT tx_new.patient_id "
            + " FROM (SELECT patient_id, MIN(art_start_date) art_start_date FROM  ( "
            + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
            + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
            + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
            + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
            + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
            + " GROUP BY  p.patient_id, e.encounter_datetime "
            + " ) art_start "
            + " GROUP BY patient_id "
            + " ) tx_new "
            + " inner join "
            + " ( "
            + " select p.patient_id, min(cc.encounter_datetime) as encounter_datetime "
            + " from patient p "
            + " inner join encounter cc ON p.patient_id = cc.patient_id "
            + " inner join obs o ON cc.encounter_id = o.encounter_id "
            + " WHERE cc.voided = 0 AND cc.encounter_type = 6 AND cc.location_id = :location AND cc.encounter_datetime >= :startInclusionDate AND cc.encounter_datetime <= :endInclusionDate AND "
            + " o.concept_id = 1982 AND o.value_coded = 1065 AND o.voided = 0 "
            + " group by p.patient_id "
            + " ) consulta ON consulta.patient_id = tx_new.patient_id "
            + " INNER JOIN person pe ON tx_new.patient_id = pe.person_id "
            + " where consulta.encounter_datetime > tx_new.art_start_date AND pe.gender = 'F' "
            + " AND (TIMESTAMPDIFF(year, birthdate, art_start_date)) >= 15 AND birthdate IS NOT NULL AND pe.voided = 0 "
            + " AND (TIMESTAMPDIFF(Month, art_start_date, :endInclusionDate)) >= 3 ";

    public static final String
        findPatientsWhoAreRequestForLaboratoryInvestigationsInclusionPeriodByB3 =
            " select patient_id from ( SELECT tx_new.patient_id, min(fisrtConsultation.encounter_datetime) AS encounter_datetime FROM (  "
                + " SELECT patient_id, MIN(art_start_date) art_start_date FROM ( "
                + " SELECT p.patient_id, MIN(value_datetime) art_start_date FROM patient p "
                + " INNER JOIN encounter e ON p.patient_id = e.patient_id "
                + " INNER JOIN obs o ON e.encounter_id = o.encounter_id "
                + " WHERE p.voided = 0 AND e.voided = 0 AND o.voided = 0 AND e.encounter_type = 53 "
                + " AND o.concept_id = 1190 AND o.value_datetime is NOT NULL AND o.value_datetime <= :endInclusionDate AND e.location_id = :location "
                + " GROUP BY p.patient_id "
                + " ) art_start "
                + " GROUP BY patient_id  "
                + " ) tx_new "
                + " INNER JOIN "
                + " ( "
                + " SELECT p.patient_id, cc.encounter_datetime as encounter_datetime "
                + " FROM 	patient p "
                + " INNER JOIN encounter cc ON p.patient_id = cc.patient_id "
                + " INNER JOIN obs o ON cc.encounter_id = o.encounter_id "
                + " WHERE 	cc.voided = 0 AND cc.encounter_type = 6  AND cc.location_id = :location "
                + " AND o.concept_id = 23722 AND o.value_coded = 856 AND o.voided = 0 "
                + " ) fisrtConsultation ON fisrtConsultation.patient_id = tx_new.patient_id "
                + " INNER JOIN person pe ON tx_new.patient_id = pe.person_id "
                + " WHERE fisrtConsultation.encounter_datetime BETWEEN date_add(tx_new.art_start_date, INTERVAL 80 DAY) AND date_add(tx_new.art_start_date, INTERVAL 130 DAY) "
                + " AND (TIMESTAMPDIFF(year, birthdate, :endInclusionDate)) >= 15 AND birthdate IS NOT NULL AND pe.voided = 0 AND pe.gender = 'F' "
                + " GROUP BY tx_new.patient_id "
                + ") result";

    public static final String
        findPatientsWhoAreRequestForLaboratoryInvestigationAndPregnantInclusionPeriodByB4 =
            " SELECT fisrtConsultation.patient_id "
                + " FROM "
                + " ( "
                + " SELECT p.patient_id, min(cc.encounter_datetime) as encounter_datetime "
                + " FROM patient p "
                + " INNER JOIN encounter cc ON p.patient_id = cc.patient_id "
                + " INNER JOIN obs o ON cc.encounter_id = o.encounter_id "
                + " WHERE cc.voided = 0 AND cc.encounter_type = 6  AND cc.location_id = :location "
                + " AND cc.encounter_datetime BETWEEN :startInclusionDate AND :endInclusionDate "
                + " AND o.concept_id = 1982 AND o.value_coded = 1065 AND o.voided = 0 "
                + " GROUP BY p.patient_id "
                + " ) fisrtConsultation "
                + " INNER JOIN obs cv ON cv.person_id = fisrtConsultation.patient_id "
                + " INNER JOIN person pe ON fisrtConsultation.patient_id = pe.person_id "
                + " WHERE cv.concept_id = 23722 AND cv.value_coded = 856 AND cv.voided = 0  AND fisrtConsultation.encounter_datetime=cv.obs_datetime"
                + " AND (TIMESTAMPDIFF(year, birthdate, :endInclusionDate)) >= 15 AND birthdate IS NOT NULL "
                + " AND pe.voided = 0 AND pe.gender = 'F' ";
  }
}
