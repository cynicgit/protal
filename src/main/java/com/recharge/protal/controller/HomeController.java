package com.recharge.protal.controller;

import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.recharge.protal.common.ServerResponse;
import com.recharge.protal.config.Const;
import com.recharge.protal.entity.ApiResult;
import com.recharge.protal.entity.Article;
import com.recharge.protal.entity.User;
import com.recharge.protal.exception.BusinessException;
import com.recharge.protal.service.ArticleService;
import com.recharge.protal.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private JedisUtils jedisUtils;

    @Autowired
    private ArticleService articleService;

    @Value("${com.recharge.createUrl}")
    private String createUrl;
    @Value("${com.recharge.loginUrl}")
    private String loginUrl;
    @Value("${com.recharge.logoutUrl}")
    private String logoutUrl;
    @Value("${com.recharge.changePasswordUrl}")
    private String changePasswordUrl;

    @PostMapping("/login")
    @ResponseBody
    public ServerResponse login(User user, String code, HttpSession session) {
        String imageCode = (String) session.getAttribute(Const.CODE);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException("验证码不能为空");
        }
        if (!code.equals(imageCode)) {
            throw new BusinessException("验证码错误");
        }

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("account", user.getAccount());
        requestEntity.add("password", user.getPassword());
        requestEntity.add("device_id", user.getDeviceId());
        requestEntity.add("platform", user.getPlatform());
        requestEntity.add("sku", user.getSku());
        String s = restTemplate.postForObject(loginUrl, requestEntity, String.class);
        if (s != null) {
            ApiResult apiResult = JsonUtils.jsonToPojo(s, ApiResult.class);
            user.setToken(apiResult.getResult());
            session.setAttribute(Const.USER_KEY, user);
        }
        return ServerResponse.success(s);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, String token) {
        User user = (User) session.getAttribute(Const.USER_KEY);
        session.removeAttribute(Const.USER_KEY);
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("token", token == null ? user.getToken() : token);
        String s = restTemplate.postForObject(logoutUrl, requestEntity, String.class);
        return "redirect:/";
    }

    @PostMapping("/register")
    @ResponseBody
    public ServerResponse register(User user, String code, HttpSession session) {
        String imageCode = (String) session.getAttribute(Const.CODE);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException("验证码不能为空");
        }
        if (!code.equals(imageCode)) {
            throw new BusinessException("验证码错误");
        }

        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("account", user.getAccount());
        requestEntity.add("password", user.getPassword());
        requestEntity.add("device_id", user.getDeviceId());
        String s = restTemplate.postForObject(createUrl, requestEntity, String.class);
        return ServerResponse.success(s);
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ServerResponse changePassword(String token, String oldPass, String newPass, HttpSession session) {
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        if (StringUtils.isEmpty(token)) {
            token = ((User)session.getAttribute(Const.USER_KEY)).getToken();
        }
        requestEntity.add("token", token);
        requestEntity.add("old", oldPass);
        requestEntity.add("new", newPass);
        String s = restTemplate.postForObject(changePasswordUrl, requestEntity, String.class);
        return ServerResponse.success(s);
    }


    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        PageInfo<Article> pageInfo = articleService.getArticles(1, 5);
        model.addAttribute("articles", pageInfo.getList());
        return "index";
    }

    @GetMapping("/download")
    public String download() {
        return "download";
    }



//    @PostMapping("/getTokenUser")
//    @ResponseBody
//    public String getTokenUser(String token) {
//        return jedisUtils.getJedis("token_" + token);
//    }




    @GetMapping("/imageCode")
    public void imageCode(HttpServletResponse response, HttpSession session) {
        String text = defaultKaptcha.createText();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        session.setAttribute(Const.CODE, text);
        ServletOutputStream out = null;
        try {
            BufferedImage bi = defaultKaptcha.createImage(text);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {

        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @RequestMapping("/contactus")
    public String index_contactus() {
        return "index_contactus";
    }

    @RequestMapping("/parcare")
    public String index_parcare() {
        return "index_parcare";
    }

    @RequestMapping("/article")
    public String article_article() {
        return "article_article";
    }

    @RequestMapping("/article/{id}")
    public String article(@PathVariable Integer id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article_article";
    }



    @RequestMapping("/list_article")
    public String list_article() {
        return "list_article";
    }

}
