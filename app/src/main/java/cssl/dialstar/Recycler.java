package cssl.dialstar;

/*
  Created by cats on 20/11/17.
 */

public class Recycler {
    private String title;
    private String count;

    public Recycler(String title, String count) {

        this.title = title;
        this.count = count;

    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
