package sbp.hack.hackdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sbp.hack.hackdemo.repository.TableRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public List<String> getDictTables() {
        return tableRepository.getTables()
                .stream()
                .filter(this::isDict)
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> listDicts() {
        List<String> referencesTables = tableRepository.getTables()
                .stream()
                .filter(name -> name.contains("_reference")
                        && !name.contains("_referencechngr") && !name.contains("_vt"))
                .collect(Collectors.toList());
        Map<String, List<String>> response = new HashMap<>();

        referencesTables.forEach(refTableName -> tryFetch(refTableName, response));

        return response;
    }

    private void tryFetch(String refTableName, Map<String, List<String>> response) {
        try {
            response.put(refTableName, tableRepository.getDescriptionFromReferenceTable(refTableName));
        } catch (Exception ignored) {
        }
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

    public List<String> getDependentTables(String tableName) {
        return tableRepository.getDependentTables(tableName);
    }
}
