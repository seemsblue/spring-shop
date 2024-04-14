package com.apple.myShop.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@Controller
public class ItemController {
    
    //디펜던시 인젝션 파트
    //spring이 이렇게 service 등에서 자동으로 뽑아주는 class를 bean이라 함
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    @Autowired  //@RequiredArgsConstructor 안쓰면 이 줄부터 써야함
    public ItemController(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    @GetMapping("/list")
    String list(Model model){
        List<Item> result = itemService.findAllItem();  //겁나쓸데없이 한줄짜리 함수 만들어봤고요
        model.addAttribute("name",result);
        return "list.html";    //기본폴더는 static 폴더
    }

    @GetMapping("/write-list")
    String writeList(Model model){
        return "write.html";
    }

    @PostMapping("/addItem")
    String addPost(String itemName, Integer itemPrice){
        if(itemName.isEmpty() ||itemPrice==null){
            return "redirect:/list";
        }
        itemService.saveItem(itemName,itemPrice);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model){
        Item item = itemService.findItemById(id);
        model.addAttribute("item",item);
        return "listDetail.html";
    }


    @GetMapping("/edit-list/{id}")
    String editDetail(@PathVariable Integer id, Model model){
        Item item = itemService.findItemById(id);
        model.addAttribute("item",item);
        return "editDetail.html";
    }
    @PostMapping("/editItem")
    String editPost(Integer itemId, String itemName, Integer itemPrice){
        if(itemName.isEmpty() ||itemPrice==null){
            return "redirect:/list";
        }
        itemService.editItem(itemId,itemName,itemPrice);
        return "redirect:/list";
    }
    @DeleteMapping("/deleteItem")
    ResponseEntity<String> deletePost(@RequestParam Integer id){
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제됨");
    }
}
