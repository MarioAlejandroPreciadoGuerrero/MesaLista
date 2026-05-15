/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.AreaDTO;
import DTO.MenuDTO;
import DTO.MesaDTO;
import DTO.PlatilloDTO;
import DTO.RestauranteDTO;
import Interface.IMapper;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class RestauranteMapper implements IMapper<RestauranteDTO> {

    @Override
    public Document toDocument(RestauranteDTO dto) {
        if (dto == null) {
            return null;
        }
        Document doc = new Document("nombre", dto.getNombre())
                .append("capacidadTotal", dto.getCapacidadTotal())
                .append("areas", mapearAreasADocumentos(dto.getAreas()))
                .append("menu", mapearMenuADocumento(dto.getMenu()));
        if (dto.getId() != null) {
            doc.append("_id", new ObjectId(dto.getId()));
        }
        return doc;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RestauranteDTO toDto(Document doc) {
        if (doc == null) {
            return null;
        }
        return new RestauranteDTO(
                doc.getObjectId("_id") != null ? doc.getObjectId("_id").toHexString() : null,
                doc.getString("nombre"),
                doc.getInteger("capacidadTotal"),
                mapearDocumentosAAreasDTO((List<Document>) doc.get("areas")),
                mapearDocumentoAMenuDTO((Document) doc.get("menu"))
        );
    }

    private List<Document> mapearAreasADocumentos(List<AreaDTO> areas) {
        if (areas == null) {
            return new ArrayList<>();
        }
        List<Document> docs = new ArrayList<>();
        for (AreaDTO area : areas) {
            List<Document> mesasDocs = new ArrayList<>();
            if (area.getMesas() != null) {
                for (MesaDTO mesa : area.getMesas()) {
                    mesasDocs.add(new Document("numero", mesa.getNumero()).append("capacidadMesa", mesa.getCapacidadMesa()));
                }
            }
            docs.add(new Document("nombre", area.getNombre()).append("capacidadMax", area.getCapacidadMax()).append("mesas", mesasDocs));
        }
        return docs;
    }

    private Document mapearMenuADocumento(MenuDTO menu) {
        if (menu == null || menu.getListaPlatillos() == null) {
            return new Document();
        }
        List<Document> platillosDocs = new ArrayList<>();
        for (PlatilloDTO platillo : menu.getListaPlatillos()) {
            platillosDocs.add(new Document("nombrePlatillo", platillo.getNombrePlatillo())
                    .append("precio", platillo.getPrecio())
                    .append("descripcion", platillo.getDescripcion()));
        }
        return new Document("listaPlatillos", platillosDocs);
    }

    @SuppressWarnings("unchecked")
    private List<AreaDTO> mapearDocumentosAAreasDTO(List<Document> docs) {
        if (docs == null) {
            return new ArrayList<>();
        }
        List<AreaDTO> areas = new ArrayList<>();
        for (Document doc : docs) {
            List<MesaDTO> mesas = new ArrayList<>();
            List<Document> mesasDocs = (List<Document>) doc.get("mesas");
            if (mesasDocs != null) {
                for (Document mDoc : mesasDocs) {
                    mesas.add(new MesaDTO(mDoc.getInteger("numero"), mDoc.getInteger("capacidadMesa")));
                }
            }
            areas.add(new AreaDTO(doc.getString("nombre"), doc.getInteger("capacidadMax"), mesas));
        }
        return areas;
    }

    @SuppressWarnings("unchecked")
    private MenuDTO mapearDocumentoAMenuDTO(Document doc) {
        if (doc == null) {
            return new MenuDTO(new ArrayList<>());
        }
        List<PlatilloDTO> platillos = new ArrayList<>();
        List<Document> platillosDocs = (List<Document>) doc.get("listaPlatillos");
        if (platillosDocs != null) {
            for (Document pDoc : platillosDocs) {
                platillos.add(new PlatilloDTO(pDoc.getString("nombrePlatillo"), pDoc.getDouble("precio"), pDoc.getString("descripcion")));
            }
        }
        return new MenuDTO(platillos);
    }
}
