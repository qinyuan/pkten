package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;

import java.util.List;

public interface ResultPredictor {
    void setOldRecords(List<DrawnRecord> records);

    /**
     * predict next phase
     *
     * @return Prediction of next phase
     */
    ResultPrediction predict();

    /**
     * predict certain phase
     *
     * @param phase phase to predict
     * @return Prediction of certain phase
     */
    ResultPrediction predict(int phase);

    /**
     * method to set how many old record used to predict new phase
     *
     * @param referSize record size used to predict new phase
     */
    void setReferSize(int referSize);
}
