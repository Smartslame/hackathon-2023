package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.repository.TableRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;
    public List<String> getDictTables() {
        return tableRepository.getTables().stream().filter(this::isDict).collect(Collectors.toList());
    }

    public Map<String, List<String>> listDicts() {
        List<String> referencesTables = tableRepository.getTables().stream().filter(name -> name.contains("_reference") && !name.contains("_referencechngr") && !name.contains("_vt")).collect(Collectors.toList());
        Map<String, List<String>> response = new HashMap<>();
        for (String refName : referencesTables) {
            System.out.println("Try to fetch " + refName);
            try {
                response.put(refName, tableRepository.getDescriptionFromReferenceTable(refName));
            } catch (Exception exception) {

            }
        }

        return response;
    }

    public List<String> getNonDictTables() {
        return tableRepository.getTables().stream().filter(name -> !isDict(name)).collect(Collectors.toList());
    }

    public boolean isDict(String tableName) {
        return tableName.contains("_const") ||
               tableName.contains("_reference") ||
               tableName.contains("_enum") ||
               tableName.equals("dbschema") ||
               tableName.contains("_scheduledjobs") ||
               tableName.contains("_node") ||
               tableName.contains("_documentjournal") ||
               tableName.contains("_seq") ||
               tableName.contains("_accumrg") ||
               tableName.contains("_inforg") ||
               tableName.contains("_acc") ||
               tableName.contains("_accrg") ||
               tableName.contains("_ckind") ||
               tableName.contains("_crg") ||
               tableName.contains("_task");

    }
}
