package org.migus.web.content.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.migus.web.content.types.Content;
import org.migus.web.content.types.Contents;
import org.migus.web.content.types.Ids;

@Path("/")
public interface ContentServer {

	@GET
	@Path("/author/{author}")
	public Contents getByAuthor(@PathParam("author") String author);

	@PUT
	@Path("/{id}")
	public Content add(@PathParam("id") String id, Content newContent);
	
	@GET
	@Path("/{id}")
	public Content get(@PathParam("id") String id);

	@GET
	public Ids getIds();

	@POST
	public Content add(Content newContent);

	@PUT
	@Path("/{id}/text")
	public String updateText(@PathParam("id") String id, String text);
	
	@PUT
	@Path("/{id}/title")
	public String updateTitle(@PathParam("id") String id, String title);
}
