package com.hub.wisehub.feature.FT001;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hub.wisehub.data.dto.AtualizarUsuarioDTO;
import com.hub.wisehub.data.dto.CriarUsuarioDTO;
import com.hub.wisehub.data.entity.Usuario;
import com.hub.wisehub.data.repository.UsuarioRepository;
import com.hub.wisehub.feature.FT002.EmailService;
import com.hub.wisehub.feature.FT003.TerceirosService;
import com.hub.wisehub.feature.UtilsService;

@Service
public class CrudService {

    @Autowired private EmailService mailService;
    @Autowired private UtilsService utilsService;
    @Autowired private TerceirosService terceiroService;
    @Autowired private UsuarioRepository usuarioRepository;

    public ResponseEntity<ObjectNode> criarUsuario(CriarUsuarioDTO json) {
        try {
            Usuario usuario = this.preencherUsuario(json);
            if(usuario != null){
                usuario = usuarioRepository.save(usuario);
                mailService.enviarEmailCriacaoUsuario(usuario.getEmail(), "Criação de conta na wiseHub", "Olá Hooper, "+usuario.getLogin()+" Seja bem vindo(a) aos serviços WiseHub agora que sua conta foi criada você pode ter acesso as informações sobre o seu time e seu jogador favorito da NBA sempre que quiser !!!\n");
                return ResponseEntity.status(HttpStatus.CREATED).body(utilsService.responseSucesso());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("O usuário não pode ser cadastrado já existe um usuário com o mesmo login"));   
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("O usuário não pode ser cadastrado por favor verifique as informações de requisição"));
        }
    }

    protected Usuario preencherUsuario(CriarUsuarioDTO json){
        Usuario usuario = null;
        usuario = usuarioRepository.findUsuarioByLogin(json.getLogin());
        if(usuario == null){
            usuario = new Usuario();
            usuario.setEmail(json.getEmail());
            usuario.setLogin(json.getLogin());
            usuario.setSenha(Base64.getEncoder().encodeToString(json.getSenha().getBytes()));
            usuario.setJogador(json.getJogador());
            usuario.setTime(json.getTime());
            return usuario;
        }
        return null;
    }

    public ResponseEntity<ObjectNode> atualizarUsuario(AtualizarUsuarioDTO json) {
        try {
            Usuario usuario = usuarioRepository.findById(json.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            BeanUtils.copyProperties(json, usuario);
            usuario = usuarioRepository.save(usuario);
            mailService.enviarEmailCriacaoUsuario(usuario.getEmail(), "Atualização de conta na wiseHub", "Olá Hooper, "+usuario.getLogin()+" Sua conta na WiseHub foi atualizada com sucessoo !!");
            return ResponseEntity.status(HttpStatus.OK).body(utilsService.responseSucesso());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("Usuário não encontrado"));
        }
    }


    public List<Usuario> pesquisarUsuarios() {
        return usuarioRepository.findAll();
    }

    public ResponseEntity<ObjectNode> pesquisarPorId(Long id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode usuario = mapper.valueToTree(usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
            ObjectNode nbaInfo = terceiroService.pesquisarTimeEJogador(usuario.get("jogador").asText(), usuario.get("time").asText());
            usuario.put("time", nbaInfo.get("time"));
            usuario.put("jogador", nbaInfo.get("jogador"));
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("Usuário não encontrado"));
        }
    }

    public ResponseEntity<ObjectNode> deleteUser(Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            mailService.enviarEmailCriacaoUsuario(usuario.getEmail(), "Exclusão de conta na wiseHub", "Olá Hooper, "+usuario.getLogin()+" É triste saber que um Hooper como você se aposentou sua conta da WiseHub, espero que nas próximas seasons nós possamos nos ver novamente !!");
            usuarioRepository.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(utilsService.responseSucesso()); 
        } catch(Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(utilsService.response("O usuário não foi encontrado por favor revise o Id para deleção"));
        }
    }

}
