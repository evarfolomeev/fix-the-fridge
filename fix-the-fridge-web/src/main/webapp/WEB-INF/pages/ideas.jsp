<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="gen" %>
<%@ include file="/WEB-INF/pages/taglibs.jsp" %>

<gen:page-template>
    <jsp:attribute name="header">
        <script>
            function uploadImageAndPostIdea(idea) {
                if ($("#fileUploadButton").prop("files").length == 0) {
                    postIdea(idea);
                } else {
                    $("#attachImageForm").ajaxForm({
                        dataType: "text",
                        success: function(data) {
                            idea.attachmentName = data;
                            postIdea(idea);
                        }
                    }).submit();
                }
            }
            function showIdea(idea, prepend) {
                var panel = $(".hiddenIdeaTemplatePanel").clone(true);
                panel.removeClass("hiddenIdeaTemplatePanel");
                if (prepend) {
                    $("#ideasPanel").prepend(panel);
                } else {
                    $("#ideasPanel").append(panel);
                }
                panel.find(".ideaId").val(idea.id);
                panel.find(".idea-title-p").append(idea.title);
                panel.find(".idea-description-p").append(idea.description);
                panel.find(".like-button").text("+" + idea.voters);
                var avatar = "${contextPath}/png/default_ava.png";
                if (idea.user.avatarName != null && idea.user.avatarName != "") {
                    avatar ="/fix-the-fridge-web/api/attachment/get/" + idea.user.avatarName;
                }
                panel.find(".avatar-panel").append("<img src='" + avatar + "' class='img-circle' width='25px' height='25px'/>");
                panel.find(".author-panel").append(idea.user.nickName);
//                panel.find(".date-panel").append(idea.creationDate);
                panel.find(".comment-span").text(" " + idea.comments);
                if (idea.votedByCurrentUser == true) {
                    panel.find(".like-button").addClass("btn-primary");
                }
                if (idea.attachmentName != null && idea.attachmentName != "") {
                    panel.find(".panel-image").append("<img src='/fix-the-fridge-web/api/attachment/get/" + idea.attachmentName + "' class='img' />");
                }
            }
            function postIdea(idea) {
                $.ajax({
                    type: "POST",
                    url: "idea/save",
                    contentType: 'application/json',
                    data: JSON.stringify(idea),
                    dataType: "json",
                    success: function(savedIdea) {
                        showIdea(savedIdea, true);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
                $("#ideaTitleInput").val("");
                $("#descriptionTextArea").val("");
            }
            function postComment(panel, comment) {
                console.log("NEW: " + JSON.stringify(comment));
                $.ajax({
                    type: "POST",
                    url: "comment/save",
                    contentType: 'application/json',
                    data: JSON.stringify(comment),
                    dataType: "json",
                    success: function(savedComment) {
                        console.log("SAVED!" + savedComment);
                        showOneComment(panel, savedComment);
                    },
                    error: function(error) {
                        console.log("ERROR!" + error.message);
                    }
                });
            }
            function showOneComment(panel, comment) {
                var oneCommentPanel = $(".one-comment-panel-to-remove").clone(true);
                oneCommentPanel.removeClass("hiddenPanel one-comment-panel-to-remove");
                panel.append(oneCommentPanel);
                var avatar = "${contextPath}/png/default_ava.png";
                if (comment.author.avatarName != null && comment.author.avatarName != "") {
                    avatar = "/fix-the-fridge-web/api/attachment/get/" + comment.author.avatarName;
                }
                oneCommentPanel.find(".avatar-panel").append("<img src='" + avatar + "' class='img-circle' width='25px' height='25px'/>");
                oneCommentPanel.find(".author-panel").append(comment.author.nickName);
//                oneCommentPanel.find(".date-panel").append(comment.creationDate);
                oneCommentPanel.find(".one-comment-span").text(comment.text);
                oneCommentPanel.find(".commentId").val(comment.id);
                oneCommentPanel.find(".like-comment-button").text("+" + comment.voters);
                if (comment.votedByCurrentUser == true) {
                    oneCommentPanel.find(".like-comment-button").addClass("btn-primary");
                }
            }
            function showComments(panel, ideaId) {
                $.ajax({
                    type: "GET",
                    url: "comment/get-by-idea?ideaId=" + ideaId,
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(comments) {
                        comments.forEach(function(comment) {
                            showOneComment(panel, comment);
                        });
                        panel.closest(".main-comment-panel").slideDown("fast");
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
            function voteForIdea(ideaId, button){
                $.ajax({
                    type: "POST",
                    url: "idea/vote?ideaId=" + ideaId,
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(idea) {
                        if (idea.votedByCurrentUser == true) {
                            button.addClass("btn-primary");
                        } else {
                            button.removeClass("btn-primary");
                        }
                        button.text("+" + idea.voters);
                    },
                    error: function(error) {
                        alert(error);
                    }
                })

            }
            function voteForComment(commentId, button){
                $.ajax({
                    type: "POST",
                    url: "comment/vote?commentId=" + commentId,
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(commentId) {
                        button.text("+" + commentId.voters);
                        if (commentId.votedByCurrentUser == true) {
                            button.addClass("btn-primary");
                        } else {
                            button.removeClass("btn-primary");
                        }
                    },
                    error: function(error) {
                        alert(error);
                    }
                })
            }
            function showIdeas(ideas) {
                $("#ideasPanel").html("");
                ideas.forEach(function(idea) {
                    showIdea(idea, false);
                })
            }
            function showMyIdeas() {
                $.ajax({
                    type: "GET",
                    url: "idea/get-my",
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(ideas) {
                        showIdeas(ideas);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
            function showHottest() {
                $.ajax({
                    type: "GET",
                    url: "idea/get-all-hottest",
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(ideas) {
                        showIdeas(ideas);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
            function showLatest() {
                console.log("HERE");
                $.ajax({
                    type: "GET",
                    url: "idea/get-all-newest",
                    contentType: 'application/json',
                    dataType: "json",
                    success: function(ideas) {
                        console.log(ideas);
                        showIdeas(ideas);
                    },
                    error: function(error) {
                        alert(error);
                    }
                });
            }
            $(document).ready(function() {
                $("#addDescriptionButton").click(function() {
                    $("#descriptionTextArea").slideDown("fast");
                });
                $("#addImageButton").click(function() {
                    $("#fileUploadButton").click();
                });
                $("#postButton").click(function() {
                    var idea = {
                        title: $("#ideaTitleInput").val(),
                        description: $("#descriptionTextArea").val()
                    };
                    uploadImageAndPostIdea(idea);
                });
                $(".button-add-comment").click(function() {
                    var commentsPanel = $(this).closest(".one-idea-panel").find(".user-comments-panel");
                    var textInput = $(this).closest(".input-group").find(".comment-text");
                    var comment = {
                        text: textInput.val(),
                        ideaDto: {
                            id: $(this).closest(".one-idea-panel").find(".ideaId").val()
                        }
                    };
                    textInput.val("");
                    var commentsSpan = $(this).closest(".one-idea-panel").find(".comment-span");
                    commentsSpan.text(" " + (parseInt(commentsSpan.text()) + 1));
                    postComment(commentsPanel, comment);
                  });

                $(".like-button").click(function(){
                    var ideaId = $(this).closest(".one-idea-panel").find(".ideaId").attr("value");
                    voteForIdea(ideaId, $(this));

                });
                $(".comment-button").click(function() {
                    var ideaId = $(this).closest(".one-idea-panel").find(".ideaId").attr("value");
                    var commentsPanel = $(this).closest(".one-idea-panel").find(".user-comments-panel");
                    var showHidePanel = $(this).closest(".one-idea-panel").find(".main-comment-panel");
                    commentsPanel.html("");
                    if ($(this).hasClass("active")) {
                        showHidePanel.slideUp("fast");
                        $(this).removeClass("active");
                    } else {
                        $(this).addClass("active");
                        showComments(commentsPanel, ideaId);
                    }
                });
                $(".like-comment-button").click(function(){
                    var commentId = $(this).closest(".one-comment-panel").find(".commentId").attr("value");
                    console.log("@@@" + commentId);
                    voteForComment(commentId, $(this));
                });
                $("img.user-avatar-img").click(function() {
                    $("#fileUploadButton").click();
                });
                $(".save-image").click(function() {
                    $("#attachImageForm").ajaxForm({
                        dataType: "text",
                        success: function(data) {
                            $.ajax({
                                type: "GET",
                                url: "user/update-avatar?avatarId=" + data,
                                contentType: 'application/json',
                                dataType: "json",
                                success: function(user) {
                                    console.log(user);
                                },
                                error: function(error) {
                                    alert(error);
                                }
                            });
                        }
                    }).submit();
                });
                <c:forEach items="${ideas}" var="idea">
                    var ideaToShow = {
                        id: "${idea.id}",
                        title: "${idea.title}",
                        description: "${idea.description}",
                        voters: "${idea.voters}",
                        user: {
                            nickName: "${idea.user.nickName}",
                            avatarName: "${idea.user.avatarName}"
                        },
                        comments: "${idea.comments}",
                        status: "${idea.status}",
                        attachmentName: "${idea.attachmentName}",
                        votedByCurrentUser: "${idea.votedByCurrentUser}",
                        creationDate: "${idea.creationDate}"
                    };
                    showIdea(ideaToShow, false);
                </c:forEach>
            });
        </script>
    </jsp:attribute>
    <jsp:attribute name="leftPanel">
    </jsp:attribute>
    <jsp:attribute name="rightPanel">
        <div class="panel panel-default" style="margin-top: 15px">
            <div class="panel-body"  >
                <form id="newIdeaForm" class="form-horizontal" role="form" >
                    <div class="input-group">
                        <input id="ideaTitleInput" type="text" class="form-control" placeholder="Write your idea here">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span class="caret"></span></button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a id="addDescriptionButton" href="#">Add description</a></li>
                                <li><a id="addImageButton" href="#">Add image</a></li>
                            </ul>
                            <button id="postButton" class="btn btn-default" type="button" style="background-color: #0088cc; color: #ffffff; text-align: center">Post</button>
                        </div><!-- /btn-group -->
                    </div><!-- /input-group -->
                    <textarea id="descriptionTextArea" class="description-textarea form-control" rows="3" placeholder="Explain your idea"></textarea>
                </form>
                <form id="attachImageForm" method="post" action="/fix-the-fridge-web/api/attachment/upload" enctype="multipart/form-data">
                    <input id="fileUploadButton" type="file" name="file"/>
                </form>
            </div>
        </div>

            <div class="one-idea-panel hiddenIdeaTemplatePanel">
                <div class="panel panel-default" >
                    <input class="ideaId" type="hidden"/>
                    <div class="panel-heading" style="overflow: hidden">
                        <div class="panel-image"></div>
                        <div>
                            <div class="panel-title" style="">
                                <p class="idea-title-p no-wrap"></p>
                            </div>
                            <p class="idea-description-p no-wrap"></p>
                        </div>
                    </div>
                    <div class="panel-body" style="padding: 5px 15px">
                        <div class="avatar-panel" style="float: left">
                        </div>
                        <b style="color: #776">
                            <div class="author-panel" style="float: left; margin-left: 10px">
                            </div>
                        </b>
                        <div class="date-panel" style="float: left; margin-left: 10px; color: #aaa">
                        </div>

                        <div class="idea-voters-panel" style="float: right">
                            <button type="button" class="comment-button btn btn-default btn-xs"><span class="glyphicon glyphicon-comment"></span><span class="comment-span"></span></button>
                            <button type="button"  class="like-button btn btn-default btn-xs">+1</button>
                        </div>
                    </div>
                </div>
                <div class="main-comment-panel panel panel-default">
                    <div class="user-comments-panel" style="overflow-y: scroll; max-height: 300px;"></div>
                    <div class="input-group">
                        <input type="text" class="form-control comment-text" placeholder="Add a comment">
                        <div class="input-group-btn">
                            <button class="btn btn-default button-add-comment" type="button">Add</button>
                        </div><!-- /btn-group -->
                    </div><!-- /input-group -->
                </div>
            </div>

        <div id="ideasPanel">
        </div>

        <div class="hiddenPanel one-comment-panel one-comment-panel-to-remove" style="min-height: 50px">
            <input class="commentId" type="hidden"/>
            <div style="float: left">
                <div class="avatar-panel" style="float: left">
                </div>
            </div>
            <div class="idea-voters-panel" style="float: right; margin-right: 10px">
                <button type="button" class="like-comment-button btn btn-default btn-xs">+1</button>
            </div>
            <div>
                <b>
                    <div class="author-panel" style="margin-left: 30px;">
                    </div>
                </b>
                <div style="margin-left: 30px; margin-right: 50px">
                    <span class="one-comment-span"></span>
                </div>
            </div>
            <br/>
        </div>
    </jsp:attribute>
</gen:page-template>