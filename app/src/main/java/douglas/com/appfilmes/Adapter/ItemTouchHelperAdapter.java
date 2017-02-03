package douglas.com.appfilmes.Adapter;

/**
 * Created by douglasEller on 28/01/17.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
