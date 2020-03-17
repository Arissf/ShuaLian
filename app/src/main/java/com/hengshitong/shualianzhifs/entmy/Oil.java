package com.hengshitong.shualianzhifs.entmy;

import java.util.List;

public class Oil {




    public List<gustims> guns;


        public List<gustims> getGuns() {
            return guns;
        }

        public void setGuns(List<gustims> guns) {
            this.guns = guns;
        }



    public class gustims{

        private String id;
        private String value;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


    }

}
