package com.htnova.system.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.htnova.common.base.BaseTreeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_dept")
public class Dept extends BaseTreeEntity<Dept> {
    /** 名称（中文） */
    private String name;

    /** 编码 */
    private String code;

    public SaveEvent saveEvent() {
        return new SaveEvent();
    }

    public class SaveEvent {

        public Dept getDept() {
            return Dept.this;
        }
    }

    public DeleteEvent deleteEvent() {
        return new DeleteEvent();
    }

    public class DeleteEvent {

        public Dept getDept() {
            return Dept.this;
        }
    }
}
