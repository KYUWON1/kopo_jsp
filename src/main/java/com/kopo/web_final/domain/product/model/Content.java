package com.kopo.web_final.domain.product.model;

import java.sql.Blob;
import java.time.LocalDate;

public class Content {
    private String idFile;          // ID_FILE (PK)
    private String nmOrgFile;       // NM_ORG_FILE
    private String nmSaveFile;      // NM_SAVE_FILE
    private String nmFilePath;      // NM_FILE_PATH
    private Blob boSaveFile;        // BO_SAVE_FILE
    private String nmFileExt;       // NM_FILE_EXT
    private String cdFileType;      // CD_FILE_TYPE
    private LocalDate daSave;       // DA_SAVE
    private int cnHit;              // CN_HIT
    private String idService;       // ID_SERVICE
    private String idOrgFile;       // ID_ORG_FILE
    private String noRegister;      // NO_REGISTER
    private LocalDate daFirstDate;  // DA_FIRST_DATE

    // --- Getter / Setter ---

    public String getIdFile() {
        return idFile;
    }

    public void setIdFile(String idFile) {
        this.idFile = idFile;
    }

    public String getNmOrgFile() {
        return nmOrgFile;
    }

    public void setNmOrgFile(String nmOrgFile) {
        this.nmOrgFile = nmOrgFile;
    }

    public String getNmSaveFile() {
        return nmSaveFile;
    }

    public void setNmSaveFile(String nmSaveFile) {
        this.nmSaveFile = nmSaveFile;
    }

    public String getNmFilePath() {
        return nmFilePath;
    }

    public void setNmFilePath(String nmFilePath) {
        this.nmFilePath = nmFilePath;
    }

    public Blob getBoSaveFile() {
        return boSaveFile;
    }

    public void setBoSaveFile(Blob boSaveFile) {
        this.boSaveFile = boSaveFile;
    }

    public String getNmFileExt() {
        return nmFileExt;
    }

    public void setNmFileExt(String nmFileExt) {
        this.nmFileExt = nmFileExt;
    }

    public String getCdFileType() {
        return cdFileType;
    }

    public void setCdFileType(String cdFileType) {
        this.cdFileType = cdFileType;
    }

    public LocalDate getDaSave() {
        return daSave;
    }

    public void setDaSave(LocalDate daSave) {
        this.daSave = daSave;
    }

    public int getCnHit() {
        return cnHit;
    }

    public void setCnHit(int cnHit) {
        this.cnHit = cnHit;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIdOrgFile() {
        return idOrgFile;
    }

    public void setIdOrgFile(String idOrgFile) {
        this.idOrgFile = idOrgFile;
    }

    public String getNoRegister() {
        return noRegister;
    }

    public void setNoRegister(String noRegister) {
        this.noRegister = noRegister;
    }

    public LocalDate getDaFirstDate() {
        return daFirstDate;
    }

    public void setDaFirstDate(LocalDate daFirstDate) {
        this.daFirstDate = daFirstDate;
    }
}
