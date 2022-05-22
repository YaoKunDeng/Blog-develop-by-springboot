package com.kun.web.admin;

import com.kun.pojo.Tag;
import com.kun.pojo.Type;
import com.kun.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private TagsService tagsService;


    @GetMapping("/tags")
    public String types(@PageableDefault(size = 10, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagsService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagsService.getTag(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Type());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    //后端验证还没实现成功
    //@Valiad 校验结果,与NotBlank一起用【在实体类用】
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagsService.getTagByName(tag.getName());
        if(tag1 != null){
            attributes.addFlashAttribute("message","新增失败，已有该分类");
            return "redirect:/admin/tags";
        }
//       后台验证，还没实现
//        if(result.hasErrors()){
//            return "admin/types-input";
//        }
        Tag t = tagsService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message", "新增失败");
        }else{
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagsService.getTagByName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "新增失败，已有该分类");
            return "redirect:/admin/tags";
            }
            Tag tag2 = tagsService.updateTag(id, tag);
            if (tag2 == null) {
                attributes.addFlashAttribute("message", "更新失败");
            } else {
                attributes.addFlashAttribute("message", "更新成功");
            }
            return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }


}
