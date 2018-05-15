package com.tmsx.simu;

import com.tmsx.noclient.config.HttpCenterConfig;
import com.tmsx.noclient.simu.MocoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


/**
 * Created by yang.zhou on 2017/11/23.
 */
@ContextConfiguration(classes = HttpCenterConfig.class)
public class MocoHttpTest extends AbstractTestNGSpringContextTests{

    @Autowired
    MocoClient mocoClient;

    @Test
    public void getAllPostsTest() throws Exception{
        mocoClient.getAllPosts();
    }

@Test
public void addNewPostTest() throws Exception{
        mocoClient.addNewPost();
        }

        }
