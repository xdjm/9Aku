package com.xd.commander.aku.bean;

/**
 * ${project_name} Created by Administrator on 2017/5/7 at${time}
 * <p>
 * Copyright (C) 2017 By yjm
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Other {
    private int other_imageId;
    private String other_tv;

    public String getOther_tag() {
        return other_tag;
    }

    public Other setOther_tag(String other_tag) {
        this.other_tag = other_tag;
        return this;
    }

    private String other_tag;

    public Other(int other_imageId, String other_tv,String other_tag) {
        this.other_imageId = other_imageId;
        this.other_tv = other_tv;
        this.other_tag = other_tag;
    }

    public int getOther_imageId() {
        return other_imageId;
    }

    public Other setOther_imageId(int other_imageId) {
        this.other_imageId = other_imageId;
        return this;
    }

    public String getOther_tv() {
        return other_tv;
    }

    public Other setOther_tv(String other_tv) {
        this.other_tv = other_tv;
        return this;
    }
}
