package frank.com.uimanager.bean;

import java.util.List;

/**
 * Created by frank on 2017/5/2.
 */

public class MeetingTitleBean {

    /**
     * flag : true
     * results : [{"meeting_id":"8","meeting_name":"第二场人民代表大会"},{"meeting_id":"1","meeting_name":"人民代表大会表决系统"}]
     */

    private boolean flag;
    private List<ResultsBean> results;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * meeting_id : 8
         * meeting_name : 第二场人民代表大会
         */

        private String meeting_id;
        private String meeting_name;

        public String getMeeting_id() {
            return meeting_id;
        }

        public void setMeeting_id(String meeting_id) {
            this.meeting_id = meeting_id;
        }

        public String getMeeting_name() {
            return meeting_name;
        }

        public void setMeeting_name(String meeting_name) {
            this.meeting_name = meeting_name;
        }
    }
}
