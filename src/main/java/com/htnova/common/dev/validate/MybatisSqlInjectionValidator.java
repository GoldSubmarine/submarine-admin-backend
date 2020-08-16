package com.htnova.common.dev.validate;

import com.htnova.common.dev.config.DevValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.lang.NonNull;

@Slf4j
public class MybatisSqlInjectionValidator implements DevValidator {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent applicationReadyEvent) {
        //        Configuration configuration = sqlSessionFactory.getConfiguration();
        //        // 这里之所以不这么写，是因为报错了，Map<String, XNode> sqlFragments1里 有两个entry的value不是XNode，强行塞进来了，所以取的时候报错了
        //        //参见 org.apache.ibatis.session.Configuration:954 super.put(shortKey, (V) new Ambiguity(shortKey));
        //        //        Map<String, XNode> sqlFragments1 = configuration.getSqlFragments();
        //        Collection mappedStatements = (Collection) configuration.getMappedStatements();
        //        for (Object mappedStatement : mappedStatements) {
        //            if (mappedStatement instanceof MappedStatement) {
        //                MappedStatement mappedStatement1 = (MappedStatement) mappedStatement;
        //                if (mappedStatement1.getId().contains("ApplyEditMapper")) {
        //                    System.out.println(mappedStatement1);
        //                }
        //            }
        //        }
        //        Map sqlFragments = configuration.getSqlFragments();
        //        for (Object o : sqlFragments.entrySet()) {
        //            Map.Entry entry = (Map.Entry) o;
        //            if (entry.getValue() instanceof XNode) {
        //                XNode value = (XNode) entry.getValue();
        //                System.out.println(entry.getKey());
        //                if (hasIllegalCharacter(value.toString())) {
        //                    log.warn(String.format("sql[%s]含有${}变量，可能引起sql注入", entry.getKey()));
        //                    //                    System.out.println(entry.getKey()+" "+value.toString());
        //                }
        //            }
        //        }
    }

    private static boolean hasIllegalCharacter(String sql) {
        return sql.contains("${");
    }
}
