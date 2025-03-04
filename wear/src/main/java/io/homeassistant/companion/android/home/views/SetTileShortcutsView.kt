package io.homeassistant.companion.android.home.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import com.mikepenz.iconics.compose.Image
import com.mikepenz.iconics.typeface.library.community.material.CommunityMaterial
import io.homeassistant.companion.android.data.SimplifiedEntity
import io.homeassistant.companion.android.theme.WearAppTheme
import io.homeassistant.companion.android.theme.wearColorPalette
import io.homeassistant.companion.android.util.getIcon
import io.homeassistant.companion.android.util.simplifiedEntity
import io.homeassistant.companion.android.views.ListHeader
import io.homeassistant.companion.android.views.ThemeLazyColumn
import io.homeassistant.companion.android.common.R as commonR

@Composable
fun SetTileShortcutsView(
    shortcutEntities: MutableList<SimplifiedEntity>,
    onShortcutEntitySelectionChange: (Int) -> Unit,
    isShowShortcutTextEnabled: Boolean,
    onShowShortcutTextEnabled: (Boolean) -> Unit
) {

    WearAppTheme {
        ThemeLazyColumn {
            item {
                ListHeader(id = commonR.string.shortcuts_tile)
            }
            item {
                ToggleChip(
                    modifier = Modifier.fillMaxWidth(),
                    checked = isShowShortcutTextEnabled,
                    onCheckedChange = { onShowShortcutTextEnabled(it) },
                    label = {
                        Text(stringResource(commonR.string.shortcuts_tile_text_setting))
                    },
                    appIcon = {
                        Image(
                            asset =
                            if (isShowShortcutTextEnabled)
                                CommunityMaterial.Icon.cmd_alphabetical
                            else
                                CommunityMaterial.Icon.cmd_alphabetical_off,
                            colorFilter = ColorFilter.tint(wearColorPalette.onSurface)
                        )
                    },
                    toggleControl = {
                        Icon(
                            imageVector = ToggleChipDefaults.checkboxIcon(isShowShortcutTextEnabled),
                            contentDescription = if (isShowShortcutTextEnabled)
                                stringResource(commonR.string.show)
                            else
                                stringResource(commonR.string.hide)
                        )
                    }
                )
            }
            item {
                ListHeader(id = commonR.string.shortcuts_choose)
            }
            items(shortcutEntities.size) { index ->

                val iconBitmap = getIcon(
                    shortcutEntities[index].icon,
                    shortcutEntities[index].domain,
                    LocalContext.current
                )

                Chip(
                    modifier = Modifier
                        .fillMaxWidth(),
                    icon = {
                        Image(
                            iconBitmap ?: CommunityMaterial.Icon.cmd_bookmark,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(commonR.string.shortcut_n, index + 1)
                        )
                    },
                    secondaryLabel = {
                        Text(
                            text = shortcutEntities[index].friendlyName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    onClick = { onShortcutEntitySelectionChange(index) },
                    colors = ChipDefaults.secondaryChipColors()
                )
            }
            if (shortcutEntities.size < 7) {
                item {
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { onShortcutEntitySelectionChange(shortcutEntities.size) },
                        colors = ButtonDefaults.primaryButtonColors()
                    ) {
                        Image(
                            CommunityMaterial.Icon3.cmd_plus_thick
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND)
@Composable
private fun PreviewSetTileShortcutsView() {
    SetTileShortcutsView(
        shortcutEntities = mutableListOf(simplifiedEntity),
        onShortcutEntitySelectionChange = {},
        isShowShortcutTextEnabled = true,
        onShowShortcutTextEnabled = {}
    )
}
