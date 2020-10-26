package ImageHoster.controller;
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;


    @RequestMapping(value = "/image/{imageId}/{title}/comments", method = RequestMethod.POST)
    public String postComment(@PathVariable("imageId") Integer imageId, @PathVariable("title") String title, @RequestParam("comment") String comment, Model model, HttpSession session) {
        Image image = imageService.getImage(imageId);
        User user = (User) session.getAttribute("loggeduser");

        Comment cm = new Comment();
        cm.setImage(image);
        cm.setText(comment);
        cm.setCreatedDate(new Date());
        cm.setUser(user);
        commentService.addComment(cm);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
