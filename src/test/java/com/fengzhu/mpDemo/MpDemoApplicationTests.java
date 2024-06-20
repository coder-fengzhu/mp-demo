package com.fengzhu.mpDemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fengzhu.mpDemo.entity.User;
import com.fengzhu.mpDemo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootTest
class MpDemoApplicationTests {

	@Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = User.builder()
                .name("fengzhu")
                .age(18)
                .email("abc@a.com")
                .build();
        userMapper.insert(user);
    }

    @Test
    void testSelect() {
        User user = userMapper.selectById(24L);
        System.out.println(user.getName());
    }

    @Test
    void testUpdate() {
        User user = User.builder()
                .id(27L)
                .email("fasfas@a.com").build();

        int count = userMapper.updateById(user);
        System.out.println("update success:" + count);
    }

    @Test
    void testDelete() {
        userMapper.deleteById(24L);
    }

    @Test
    void testQueryWrapper() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Map<String, Object> map = new HashMap<>();
        map.put("name", "abc");
        map.put("email", null);
        queryWrapper.allEq(map, false);
        List<User> userList = userMapper.selectList(queryWrapper);

        System.out.println(userList.size());
    }

    @Test
    void testLambdaQueryWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.likeRight(User::getName, "a%");
        lambdaQueryWrapper.between(User::getAge, 10, 20);

        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        System.out.println(userList.size());
    }

    @Test
    void testUpdateWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.ge("age", 10);
        User user = User.builder().name("123").build();

        userMapper.update(user, updateWrapper);

    }

    @Test
    void testRawSql() {
        List<User> userList = userMapper.selectRaw();
        System.out.println(userList.size());
    }


    @Test
    void testPage() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.gt(User::getAge, 10);

        Page page = new Page(1, 1);
        Page<User> userPage = userMapper.selectPage(page, lambdaQueryWrapper);
        List<User> userList = userPage.getRecords();
        long total = userPage.getTotal();
    }

    @Test
    void testVersion() {
        concurrentUpdate();
    }

    @Transactional
    void concurrentUpdate() {
        User user1 = userMapper.selectById(27L);
        User user2 = userMapper.selectById(27L);

        user1.setEmail("1@a.com");
        userMapper.updateById(user1);

        user2.setEmail("2@a.com");
        int count = userMapper.updateById(user2);
        if (count == 0) {
            System.out.println("update fail");
        } else {
            System.out.println("update success");
        }
    }




}
