package io.github.octopigeon.cptmpservice;

import io.github.octopigeon.cptmpdao.mapper.PigeonMapper;
import io.github.octopigeon.cptmpdao.model.Pigeon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PigeonService {

    @Autowired
    private PigeonMapper pigeonMapper;

    public void managePigeon(String command, String arg) {
        switch (command) {
            // add wxc
            case "add":
                Pigeon pigeon = new Pigeon();
                pigeon.setPigeonName(arg);
                pigeonMapper.addPigeon(pigeon);
                break;
            // remove
            case "remove":
                pigeonMapper.removeAllPigeons();
                break;
            // list
            case "list":
                List<Pigeon> pigeons = pigeonMapper.findAllPigeons();
                for (Pigeon pigeon1 : pigeons) {
                    System.out.println(pigeon1.getId() + " " + pigeon1.getPigeonName());
                }
                break;
            default:
        }
    }

}
