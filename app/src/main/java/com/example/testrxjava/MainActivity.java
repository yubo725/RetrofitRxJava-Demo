package com.example.testrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.testrxjava.entity.LoginResult;
import com.example.testrxjava.entity.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.100.2:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TestService service = retrofit.create(TestService.class);
        service.getUser("zhangsan")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Log.e(TAG, "accept: " + user);
                    }
                });
        service.login("wangwu", "111111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResult>() {
                    @Override
                    public void accept(@NonNull LoginResult loginResult) throws Exception {
                        Log.e(TAG, "login: " + loginResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "login: error: " + throwable);
                        // 获取非200响应的body内容
                        if (throwable instanceof HttpException) {
                            HttpException exception = (HttpException) throwable;
                            String bodyString = exception.response().errorBody().string();
                            Log.e(TAG, "login: error, body string: " + bodyString);
                        }
                    }
                });

        service.testHeaders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResult>() {
                    @Override
                    public void accept(@NonNull LoginResult loginResult) throws Exception {
                        Log.e(TAG, "headers: " + loginResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e(TAG, "headers: error: " + throwable);
                    }
                });
//        Call<ResponseBody> call = service.login("wangwu", "123456");
//        Call<ResponseBody> call = service.testHeaders();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    try {
//                        Log.e(TAG, "onResponse: " + response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//        Call<ResponseBody> call = service.getUsername("yubo");
//        String url = call.request().url().toString();
//        Log.e(TAG, "onCreate: url = " + url);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    if (response != null && response.body() != null) {
//                        Log.e(TAG, "onResponse: " + response.body().string());
//                    } else {
//                        Log.e(TAG, "onResponse: response = null");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });

//        Flowable.just("hello").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//            }
//        });

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                e.onNext("hello");
//                Log.e(TAG, "subscribe: send hello");
//                e.onNext("world");
//                Log.e(TAG, "subscribe: send word");
//                e.onComplete();
//                Log.e(TAG, "subscribe: onComplete");
//                e.onNext("after complete");
//                Log.e(TAG, "subscribe: send after complete");
//            }
//        }).map(new Function<String, String>() {
//            @Override
//            public String apply(@NonNull String s) throws Exception {
//                return s.toUpperCase();
//            }
//        }).subscribe(new Observer<String>() {
//            Disposable disponsable;
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.e(TAG, "onSubscribe: ");
//                disponsable = d;
//            }
//
//            @Override
//            public void onNext(@NonNull String s) {
//                Log.e(TAG, "onNext: " + s);
//                if ("hello".equals(s) && disponsable != null) {
//                    disponsable.dispose();
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.e(TAG, "onError: ");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "onComplete: ");
//            }
//        });

        // zip操作将两个Observable合并
//        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
//                for (int i = 0; i < 3; i++) {
//                    e.onNext(i);
//                }
//                e.onComplete();
//            }
//        });
//
//        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//                String str = "hello, this is yubo, nice to meet you!";
//                String[] arr = str.split(" ");
//                for (String item : arr) {
//                    e.onNext(item);
//                }
//                e.onComplete();
//            }
//        });
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
//                return s + ": " + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                Log.e(TAG, "accept: " + s);
//            }
//        });

        // 连接两个Observable
//        Observable.concat(Observable.just(1, 2, 3), Observable.just("hello", "world", "java"))
//                .subscribe(new Consumer<Serializable>() {
//                    @Override
//                    public void accept(@NonNull Serializable serializable) throws Exception {
//                        Log.e(TAG, "accept: " + serializable + " >>> " + serializable.getClass());
//                    }
//                });

//        Observable.just(1, 2, 3, 4, 5)
//                .flatMap(new Function<Integer, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
//                        String[] arr = new String[integer];
//                        char c = 'A';
//                        for (int i = 0; i < integer; i++) {
//                            arr[i] = String.format(Locale.SIMPLIFIED_CHINESE, "%d, %c", integer, (char)(c + i));
//                        }
//                        return Observable.fromArray(arr);
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.e(TAG, "accept: " + s);
//                    }
//                });

        // buffer(count, skip)方法将数据分成集合，每个集合中的元素个数为count个，集合的划分以skip为步长
//        Observable.just(1, 2, 3, 4, 5)
//                .buffer(3, 2)
//                .subscribe(new Consumer<List<Integer>>() {
//                    @Override
//                    public void accept(@NonNull List<Integer> integers) throws Exception {
//                        Log.e(TAG, "accept: " + integers);
//                    }
//                });

//        Log.e(TAG, "onCreate: start");
//        Observable.timer(2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        // 2秒后才执行这里
//                        Log.e(TAG, "accept: ");
//                    }
//                });

        // 不停地执行任务，初始延时为3秒，后面每次延时2秒，需要注意在onDestroy中使用dispose关闭任务
//        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
//        Observable.interval(3, 2, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    int count = 0;
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        count++;
//                        Log.e(TAG, "accept: time = " + dateFormat.format(aLong));
//                        if (count > 3) {
//                            finish();
//                        }
//                    }
//                });

        // 在next前触发操作
//        Observable.just(1, 2, 3, 4, 5)
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        Log.e(TAG, "doOnNext, accept: " + integer);
//                    }
//                })
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });

        // 跳过前面两个数
//        Observable.just(1, 2, 3, 4, 5)
//                .skip(2)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });

        // take获取前面count个数
//        Observable.just(1, 2, 3, 4, 5)
//                .take(2)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        Log.e(TAG, "accept: " + integer);
//                    }
//                });
    }
}
