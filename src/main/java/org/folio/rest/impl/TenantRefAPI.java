package org.folio.rest.impl;

import java.util.Map;
import javax.ws.rs.core.Response;
import org.folio.rest.jaxrs.model.TenantAttributes;

import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.folio.rest.tools.utils.TenantLoading;

public class TenantRefAPI extends TenantAPI {

  private static final String SAMPLE_LEAD = "sample-data";
  private static final String SAMPLE_KEY = "loadSample";
  private static final Logger log = LoggerFactory.getLogger(TenantRefAPI.class);

  @Override
  public void postTenant(TenantAttributes ta, Map<String, String> headers,
    Handler<AsyncResult<Response>> hndlr, Context cntxt) {
    log.info("postTenant");
    Vertx vertx = cntxt.owner();
    super.postTenant(ta, headers, res -> {
      if (res.failed()) {
        hndlr.handle(res);
        return;
      }
      TenantLoading tl = new TenantLoading();
      tl.addJsonIdContent(SAMPLE_KEY, SAMPLE_LEAD, "", "tags");
      tl.perform(ta, headers, vertx, res1 -> {
        if (res1.failed()) {
          hndlr.handle(io.vertx.core.Future.succeededFuture(PostTenantResponse
            .respond500WithTextPlain(res1.cause().getLocalizedMessage())));
          return;
        }
        hndlr.handle(io.vertx.core.Future.succeededFuture(PostTenantResponse
          .respond201WithApplicationJson("")));
      });
    }, cntxt);
  }

  @Override
  public void getTenant(Map<String, String> map, Handler<AsyncResult<Response>> hndlr, Context cntxt) {
    log.info("getTenant");
    super.getTenant(map, hndlr, cntxt);
  }

  @Override
  public void deleteTenant(Map<String, String> map, Handler<AsyncResult<Response>> hndlr, Context cntxt) {
    log.info("deleteTenant");
    super.deleteTenant(map, hndlr, cntxt);
  }
}
