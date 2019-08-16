package org.wulizi.myssm.service;

import org.wulizi.myssm.annotations.Service;
import org.wulizi.myssm.annotations.Transaction;
import org.wulizi.myssm.entity.User;
import org.wulizi.myssm.helper.DatabaseHelper;

import java.util.List;
import java.util.Map;

/**
 * @author wulizi
 */
@Service
public class UserService {
    @Transaction
    public List<User> getUserList() {
        String sql = "select * from user";
       return DatabaseHelper.queryEntryList(User.class,sql);
    }

    public boolean insertUser(Map<String,Object> fieldMap) {
        return DatabaseHelper.insertEntity(User.class,fieldMap);
    }
}
