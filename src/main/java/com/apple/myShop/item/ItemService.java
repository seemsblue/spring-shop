package com.apple.myShop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String itemName, Integer itemPrice){
        if (itemName.length()>=20){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "이름 너무 길어서 그냥 400에러냄"
            );
        }
        var item = new Item();
        item.setTitle(itemName);
        item.setPrice(itemPrice);
        itemRepository.save(item);
    }

    public List<Item> findAllItem(){
        return itemRepository.findAll();
    }

    public Item findItemById(Integer id){
        Optional<Item> result = itemRepository.findById(id);
        Item item;
        if(result.isPresent()){
            item = result.get();
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "찾는 아이템 id 없어서 그냥 404에러내버림"
            );
        }
        return item;
    }

    public void editItem(Integer id, String title, Integer price){
        Optional<Item> result=itemRepository.findById(id);
        if(result.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "수정하려 했는데 아이템 id 없어서 그냥 404에러내버림"
            );
        }
        if(title.isEmpty() ||price==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "수정하려 했는데 아이템 빈값으로 수정해서 404에러내버림"
            );
        }
        if(title.length()>=100){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "수정하려 했는데 제목 너무길어서 404에러내버림"
            );
        }
        if(price<0){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "수정하려 했는데 가격이 음수라 404에러내버림"
            );
        }
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
        return;
    }

    public void deleteItem(Integer id){
        if(itemRepository.findById(id).isPresent()){
            itemRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "지우려 했는데 없는 id라 그냥 404에러내버림"
            );
        }
    }
}
