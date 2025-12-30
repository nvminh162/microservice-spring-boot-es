package com.nvminh162.bookservice.query.controller;

import com.nvminh162.bookservice.query.model.BookResponseModel;
import com.nvminh162.bookservice.query.queries.GetAllBookQuery;
import com.nvminh162.bookservice.query.queries.GetBookDetailQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BookQueryController {

    QueryGateway queryGateway; // còn 1 loại nữa là DefaultQueryGateway

    @GetMapping
    public List<BookResponseModel> getAllBook() { // Direct Query, Scatter-gather query, Subscription query
        GetAllBookQuery query = new GetAllBookQuery();
        // 1. CompletableFuture<List<BookResponseModel>>
        // CompletableFuture<List<BookResponseModel>> bookFuture = queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)); // return completable-future - Direct Query - Xử lý bất đồng bộ

        /* 2. List<BookResponseModel>
        Chuyển từ CompletableFuture<List<BookResponseModel>> sang List<BookResponseModel> thêm .join() vì:
        => join() khi gọi method này sẽ blocking luồng queryGateway đợi kết quả return => bắt buộc chương trình phải tạm dừng để bỏ vào List<BookResponseModel>
        */
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }

    @GetMapping("/{bookId}")
    public BookResponseModel getBookById(@PathVariable String bookId) {
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class)).join();
    }
}
