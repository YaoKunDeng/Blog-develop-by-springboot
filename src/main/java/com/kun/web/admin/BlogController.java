package com.kun.web.admin;


import com.kun.pojo.Blog;
import com.kun.pojo.User;
import com.kun.service.BlogService;
import com.kun.service.TagsService;
import com.kun.service.TypeService;
import com.kun.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @Autowired
    private TagsService tagsService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

//    新增页面
    @GetMapping("/blogs/input")
    public String input(Model model){

//        初始化一个类的目的是在修改的时候，可以有它原本的内容
        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagsService.listTag());
        return INPUT;
    }


    @GetMapping("/blogs/{id}/input")
    public String input(Model model,@PathVariable Long id){

        Blog blog = blogService.getBlog(id);
        blog.init();

//        初始化一个类的目的是在修改的时候，可以有它原本的内容
        model.addAttribute("blog",blog);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagsService.listTag());


        return INPUT;
    }

    @PostMapping("/blogs")
//    session里面有User
//    修改和新增公共此方法，新增的时候，blog中的id值为空，修改的时候，设置的隐藏域将传一个id过来，blog判断有id就会去做修改
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagsService.listTag(blog.getTagIds()));

        //作用域升级
        Blog blog1 = null;
        //如果不做判断，哪个更新后的，数据库的创建时间字段和view字段为空，因为前端传来的blog并没有这个字段，
        //而saveBlog方法中，做了一个判断就是修改的时候，没有创建时间，修改的时候就要调用update的方法，先从数据库获取
        //原来的数据库中的东西，然后再把它存进去
        if(blog.getId()==null){
            blog1 = blogService.saveBlog(blog);
        }
        else{
            blogService.updateBlog(blog.getId(),blog);
        }
        if(blog1 == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }


}
