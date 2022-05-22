package com.kun.web.admin;

import com.kun.pojo.Type;
import com.kun.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;


    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    //后端验证还没实现成功
    //@Valiad 校验结果,与NotBlank一起用【在实体类用】
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            attributes.addFlashAttribute("message","新增失败，已有该分类");
            return "redirect:/admin/types";
        }
//       后台验证，还没实现
//        if(result.hasErrors()){
//            return "admin/types-input";
//        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message", "新增分类失败");
        }else{
            attributes.addFlashAttribute("message", "新增分类成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,@PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "新增失败，已有该分类");
            return "redirect:/admin/types";
            }
            Type type2 = typeService.updateType(id, type);
            if (type2 == null) {
                attributes.addFlashAttribute("message", "更新失败");
            } else {
                attributes.addFlashAttribute("message", "更新成功");
            }
            return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }



}
